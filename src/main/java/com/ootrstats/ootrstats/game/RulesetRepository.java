package com.ootrstats.ootrstats.game;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RulesetRepository extends CrudRepository<Ruleset, Long> {
    @EntityGraph("Ruleset.seasons")
    Optional<Ruleset> findByGameSlugAndSlug(String gameSlug, String rulesetSlug);
}
