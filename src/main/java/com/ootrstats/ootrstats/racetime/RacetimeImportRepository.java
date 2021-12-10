package com.ootrstats.ootrstats.racetime;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RacetimeImportRepository extends CrudRepository<RacetimeImport, Long> {
    Optional<RacetimeImport> findByCategorySlugAndRaceSlug(String categorySlug, String raceSlug);

    boolean existsByCategorySlugAndRaceSlug(String categorySlug, String raceSlug);
}
