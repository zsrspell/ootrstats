package com.ootrstats.ootrstats.tournament;

import com.ootrstats.ootrstats.tournament.projections.StageNameOnly;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StageRepository extends CrudRepository<Stage, Long> {
    @Query("SELECT s FROM Stage s JOIN FETCH s.tournament")
    <T> Iterable<T> findAll(Class<T> type);

    Optional<Stage> findBySlugAndTournamentSlug(String slug, String tournamentSlug);
}
