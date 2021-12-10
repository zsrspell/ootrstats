package com.ootrstats.ootrstats.racetime;

import com.ootrstats.ootrstats.speedrunner.Speedrunner;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RacetimeSpeedrunnerConflictRepository extends CrudRepository<RacetimeSpeedrunnerConflict, Long> {
    Optional<RacetimeSpeedrunnerConflict> findBySpeedrunner(Speedrunner speedrunner);
}
