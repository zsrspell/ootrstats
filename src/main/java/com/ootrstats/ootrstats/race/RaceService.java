package com.ootrstats.ootrstats.race;

import com.ootrstats.ootrstats.game.Season;
import com.ootrstats.ootrstats.race.projections.EntrantInfo;
import com.ootrstats.ootrstats.race.projections.RaceHistory;
import com.ootrstats.ootrstats.race.projections.RaceInfo;
import com.ootrstats.ootrstats.racetime.RacetimeRace;
import com.ootrstats.ootrstats.speedrunner.Speedrunner;
import com.ootrstats.ootrstats.speedrunner.SpeedrunnerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

@Service
public class RaceService {
    private final RaceRepository races;
    private final EntrantRepository entrants;
    private final SpeedrunnerRepository speedrunners;

    public RaceService(RaceRepository races, EntrantRepository entrants, SpeedrunnerRepository speedrunners) {
        this.races = races;
        this.entrants = entrants;
        this.speedrunners = speedrunners;
    }

    public Optional<Race> findById(long id) {
        return races.findByIdWithSeasonAndTournament(id, Race.class);
    }

    public Page<RaceInfo> findAll(Pageable pageable) {
        return races.findAllWithStats(pageable);
    }

    public Page<RaceHistory> findRaceHistoryBySpeedrunner(@NonNull Speedrunner speedrunner, Pageable pageable) {
        return races.findRaceHistoryBySpeedrunner(Objects.requireNonNull(speedrunner), pageable);
    }

    public Iterable<EntrantInfo> findAllEntrantsByRace(@NonNull Race race) {
        return entrants.findAllByRaceWithProfile(Objects.requireNonNull(race), Sort.by("finishTime", "name"));
    }

    public Race importRacetimeRace(@NonNull RacetimeRace racetimeRace, @NonNull Season season) throws Exception {
        return importRacetimeRace(racetimeRace, season, racetimeRace.getName());
    }


    @Transactional
    public Race importRacetimeRace(@NonNull RacetimeRace racetimeRace, @NonNull Season season, String name) throws Exception {
        Objects.requireNonNull(racetimeRace);

        if (name == null || name.isEmpty()) {
            name = racetimeRace.getName();
        }

        if (racetimeRace.isTeamRace()) {
            throw new Exception("Team races are not supported.");
        }

        // Create the Race entry first.
        var description = String.format("%s\n\n**Imported from:** https://racetime.gg%s",
                racetimeRace.getInfo(),
                racetimeRace.getUrl());

        var race = races.save(new Race(
                name,
                description,
                season,
                racetimeRace.getStartedAt().atOffset(ZoneOffset.UTC)
        ));

        var newEntrants = new ArrayList<Entrant>();
        for (var entrant : racetimeRace.getEntrants()) {
            var racetimeId = entrant.getUser().getId();
            var speedrunner = speedrunners.findByRacetimeId(racetimeId);

            // Create a new entrant.
            newEntrants.add(new Entrant(
                    race,
                    entrant.getUser().getName(),
                    speedrunner.orElseThrow(),
                    entrant.getFinishTime()
            ));
        }

        // Batch insert the entrants.
        entrants.saveAll(newEntrants);
        return race;
    }
}
