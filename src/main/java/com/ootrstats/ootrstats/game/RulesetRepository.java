package com.ootrstats.ootrstats.game;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RulesetRepository extends CrudRepository<Ruleset, Long> {
}
