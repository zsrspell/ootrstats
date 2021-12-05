package com.ootrstats.ootrstats.race;

import com.ootrstats.ootrstats.race.projections.EntrantInfo;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;

@Repository
public interface EntrantRepository extends PagingAndSortingRepository<Entrant, Long>, JpaSpecificationExecutor<Entrant> {
    @Query("SELECT e FROM Entrant e LEFT OUTER JOIN FETCH e.speedrunner LEFT OUTER JOIN FETCH e.race WHERE e.race = :race")
    Iterable<EntrantInfo> findAllByRaceWithProfile(@Param("race") Race race, Sort sort);
}
