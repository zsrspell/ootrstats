package com.ootrstats.ootrstats.game;

import com.ootrstats.ootrstats.game.projections.SeasonNamesOnly;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SeasonRepository extends CrudRepository<Season, Long> {
    @Query("SELECT s FROM Season s JOIN FETCH s.ruleset r JOIN FETCH r.game g")
    Iterable<SeasonNamesOnly> findAllSeasonNames();

    Optional<Season> findByNameAndRulesetSlugAndRulesetGameSlug(int name, String rulesetSlug, String rulesetGameSlug);
}
