package com.ootrstats.ootrstats.race;

import com.ootrstats.ootrstats.race.projections.RaceHistory;
import com.ootrstats.ootrstats.race.projections.RaceInfo;
import com.ootrstats.ootrstats.speedrunner.Speedrunner;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RaceRepository extends PagingAndSortingRepository<Race, Long>, JpaSpecificationExecutor<Race> {
    @Query("""
            SELECT r FROM Race r
                LEFT OUTER JOIN FETCH r.match m
                LEFT OUTER JOIN FETCH m.stage st
                LEFT OUTER JOIN FETCH st.tournament t
                JOIN FETCH r.season s
                JOIN FETCH s.ruleset rs
                JOIN FETCH rs.game g
            WHERE r.id = :id
            """)
    <T> Optional<T> findByIdWithSeasonAndTournament(@Param("id") long id, Class<T> type);

    @Query("""
            SELECT r.id as id,
                   r.name as name,
                   g.shortName as gameName,
                   concat(rs.shortName, ' S', s.name) as rulesetName,
                   t.name as tournamentName,
                   st.name as stageName,
                   r.startedAt as startedAt,
                   count(e) as entrantCount,
                   min(e.finishTime) as finishTime
            FROM Race r
                JOIN r.entrants e
                JOIN r.season s
                JOIN s.ruleset rs
                JOIN rs.game g
                LEFT OUTER JOIN r.match m
                LEFT OUTER JOIN m.stage st
                LEFT OUTER JOIN st.tournament t
            GROUP BY r.id, r.name, rs.shortName, g.shortName, s.name, t.name, st.name""")
    Page<RaceInfo> findAllWithStats(Pageable pageable);

    @Query("SELECT r.id as id, r.name as name, r.startedAt as startedAt, e.finishTime as finishTime FROM Race r LEFT JOIN r.entrants e LEFT JOIN e.speedrunner s WHERE s = :speedrunner")
    Page<RaceHistory> findRaceHistoryBySpeedrunner(@NonNull @Param("speedrunner") Speedrunner speedrunner, Pageable pageable);
}
