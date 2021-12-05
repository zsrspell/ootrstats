package com.ootrstats.ootrstats.speedrunner;

import com.ootrstats.ootrstats.race.Entrant;
import com.ootrstats.ootrstats.race.Race;
import com.ootrstats.ootrstats.speedrunner.projections.SpeedrunnerInfo;
import com.ootrstats.ootrstats.speedrunner.specifications.SpeedrunnerNameSpecification;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.Join;
import java.util.Optional;

@Repository
public interface SpeedrunnerRepository extends PagingAndSortingRepository<Speedrunner, Long>,
        JpaSpecificationExecutor<Speedrunner>, SearchableSpeedrunnerRepository {
    Optional<Speedrunner> findByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCase(String name);

    Optional<Speedrunner> findByRacetimeId(String racetimeId);
}
