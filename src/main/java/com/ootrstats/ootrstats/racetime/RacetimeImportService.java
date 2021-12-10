package com.ootrstats.ootrstats.racetime;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ootrstats.ootrstats.game.GameService;
import com.ootrstats.ootrstats.race.Race;
import com.ootrstats.ootrstats.race.RaceService;
import com.ootrstats.ootrstats.racetime.api.RacetimeRaceDetail;
import com.ootrstats.ootrstats.racetime.api.RacetimeStatus;
import com.ootrstats.ootrstats.racetime.exceptions.RaceNotRecordedException;
import com.ootrstats.ootrstats.racetime.exceptions.RacetimeStatusException;
import com.ootrstats.ootrstats.speedrunner.SpeedrunnerService;
import com.ootrstats.ootrstats.speedrunner.exceptions.ImportConflictException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.OffsetDateTime;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.zip.CRC32;

@Service
public class RacetimeImportService {
    private static final Logger logger = LoggerFactory.getLogger(RacetimeImportService.class);

    private final RacetimeService racetimeService;
    private final RaceService raceService;
    private final SpeedrunnerService speedrunnerService;
    private final GameService gameService;
    private final RacetimeImportRepository imports;
    private final RacetimeSpeedrunnerConflictRepository conflicts;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public RacetimeImportService(RacetimeService racetimeService, RaceService raceService, SpeedrunnerService speedrunnerService, GameService gameService, RacetimeImportRepository imports, RacetimeSpeedrunnerConflictRepository conflicts) {
        this.racetimeService = racetimeService;
        this.raceService = raceService;
        this.speedrunnerService = speedrunnerService;
        this.gameService = gameService;
        this.imports = imports;
        this.conflicts = conflicts;
        objectMapper.findAndRegisterModules();
    }

    public long calculateRaceChecksum(@NonNull RacetimeRaceDetail race) throws JsonProcessingException {
        var crc = new CRC32();
        var bytes = objectMapper.writeValueAsBytes(race);
        crc.update(bytes);
        return crc.getValue();
    }

    @NonNull
    protected RacetimeImport startRaceImport(@NonNull RacetimeRaceDetail race) throws JsonProcessingException {
        var checksum = Long.toHexString(calculateRaceChecksum(race));
        var raceSlug = race.getSlug();
        var categorySlug = race.getCategory().getSlug();

        if (!imports.existsByCategorySlugAndRaceSlug(categorySlug, race.getSlug())) {
            var raceImport = imports.save(new RacetimeImport(
                    categorySlug,
                    raceSlug,
                    checksum
            ));

            raceImport.setRecorded(race.isRecorded());
            return raceImport;
        } else {
            return imports.findByCategorySlugAndRaceSlug(categorySlug, raceSlug).orElseThrow();
        }
    }

    @Transactional
    protected void importRaceEntrants(@NonNull RacetimeImport racetimeImport, @NonNull RacetimeRaceDetail race) {
        var entrants = race.getEntrants();
        for (var entrant : entrants) {
            var user = entrant.getUser();

            try {
                var speedrunner = speedrunnerService.importRacetimeUser(user);
            } catch (ImportConflictException e) {
                var conflict = conflicts.findBySpeedrunner(e.getSpeedrunner())
                        .orElse(new RacetimeSpeedrunnerConflict(
                                user.getId(),
                                user.getName(),
                                user.getFullName(),
                                user.getTwitchName(),
                                e.getSpeedrunner()
                        ));

                racetimeImport.getConflicts().add(conflict);
                conflict.getImports().add(racetimeImport);
                conflicts.save(conflict);
            }
        }
    }

    @Transactional
    protected Race completeRacetimeImport(RacetimeImport racetimeImport) throws RaceNotRecordedException, Exception {
        // First, we must load up the race.
        var categorySlug = racetimeImport.getCategorySlug();
        var raceSlug = racetimeImport.getRaceSlug();

        if (!racetimeImport.getConflicts().isEmpty()) {
            // TODO: Dedicated exception?
            throw new RacetimeStatusException("Race has import conflicts");
        }

        var race = racetimeService.getRace(categorySlug, raceSlug).orElseThrow();
        if (!race.isRecorded()) {
            throw new RaceNotRecordedException("Race not recorded");
        }

        racetimeImport.setRecorded(true);

        if (!race.getStatus().getValue().equals(RacetimeStatus.STATUS_FINISHED)) {
            throw new RacetimeStatusException("Race not finished");
        }

        var checksum = Long.toHexString(calculateRaceChecksum(race));
        // TODO: Algorithm that tries to figure out the ruleset and season.
        var season = gameService.findSeason("ootr", "standard", 5);

        var importedRace = raceService.importRacetimeRace(race, season.orElseThrow(), race.getName());
        racetimeImport.setRace(importedRace);
        racetimeImport.setImportDate(OffsetDateTime.now());
        racetimeImport.setChecksum(checksum);
        imports.save(racetimeImport);
        logger.info("Imported race {} (checksum {}) from Racetime.gg", race.getName(), checksum);
        return importedRace;
    }

    public void importRace(RacetimeRaceDetail race) throws JsonProcessingException {
        var racetimeImport = startRaceImport(race);
        importRaceEntrants(racetimeImport, race);
        try {
            if (racetimeImport.getRace().isEmpty()) {
                completeRacetimeImport(racetimeImport);
            }
        } catch (Exception | RaceNotRecordedException e) {
            logger.info("Failed to import race {} because: {}", race.getName(), e.getMessage());
        }
    }

    private AtomicInteger pageCounter = new AtomicInteger(515);

    @Scheduled(fixedRateString = "PT5M")
    protected void importLatestRaces() throws Exception {
        var importedCategory = "ootr";

        var historicalRaces = racetimeService.getRacesByCategory(importedCategory, pageCounter.getAndIncrement());
        if (pageCounter.get() <= historicalRaces.getNumPages()) {
            logger.info("Automatically importing next page of historical race data (page {} of {})", pageCounter.get() - 1, historicalRaces.getNumPages());
            for (var race : historicalRaces.getRaces()) {
                var name = race.getName().substring(race.getName().indexOf('/') + 1); // TODO: Eww
                var targetOptional = racetimeService.getRace(importedCategory, name);
                targetOptional.ifPresent(target -> {
                    try {
                        importRace(target);
                    } catch (JsonProcessingException e) {
                        // do nothing
                    }
                });
            }
        }

        logger.info("Starting automatic import of Racetime.gg races from category '{}'", importedCategory);
        var races = racetimeService.getLatestRaces(importedCategory);

        for (var race : races) {
            var name = race.getName().substring(race.getName().indexOf('/') + 1); // TODO: Eww
            var targetOptional = racetimeService.getRace(importedCategory, name);
            targetOptional.ifPresent(target -> {
                try {
                    importRace(target);
                } catch (JsonProcessingException e) {
                    // do nothing
                }
            });
        }
    }
}
