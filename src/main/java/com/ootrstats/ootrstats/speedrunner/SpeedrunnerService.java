package com.ootrstats.ootrstats.speedrunner;

import com.ootrstats.ootrstats.racetime.api.RacetimeUser;
import com.ootrstats.ootrstats.speedrunner.exceptions.ImportConflictException;
import com.ootrstats.ootrstats.speedrunner.projections.SpeedrunnerInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class SpeedrunnerService {
    private final SpeedrunnerRepository speedrunners;
    private final TeamRepository teams;

    public SpeedrunnerService(SpeedrunnerRepository speedrunners, TeamRepository teams) {
        this.speedrunners = speedrunners;
        this.teams = teams;
    }

    public Page<Speedrunner> findAll(Pageable pageable) {
        return speedrunners.findAll(pageable);
    }

    public Page<SpeedrunnerInfo> findAll(SpeedrunnerSearchCriteria searchParams, Pageable pageable) {
        return speedrunners.findAll(searchParams, pageable);
    }

    public Optional<Speedrunner> findByName(String name) {
        return speedrunners.findByNameIgnoreCase(name);
    }

    @NonNull
    public Speedrunner importRacetimeUser(@NonNull RacetimeUser user) throws ImportConflictException {
        Objects.requireNonNull(user);
        var racetimeId = user.getId();
        var racetimeName = user.getName();
        var speedrunnerOptional = speedrunners.findByRacetimeId(racetimeId);

        if (speedrunnerOptional.isPresent()) {
            var speedrunner = speedrunnerOptional.get();
            speedrunner.setRacetimeName(user.getFullName());
            speedrunner.setTwitchChannel(user.getTwitchName());
            return speedrunners.save(speedrunner);
        } else {
            speedrunnerOptional = speedrunners.findByNameIgnoreCase(racetimeName);
            if (speedrunnerOptional.isPresent()) {
                throw new ImportConflictException(speedrunnerOptional.get());
            }

            // Safely import the new speedrunner, no conflicts here.
            var speedrunner = new Speedrunner(user.getName(), user.getId(), user.getFullName(), user.getTwitchName());
            return speedrunners.save(speedrunner);
        }
    }
}
