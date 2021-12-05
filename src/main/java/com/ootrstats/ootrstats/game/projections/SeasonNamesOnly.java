package com.ootrstats.ootrstats.game.projections;

import org.springframework.beans.factory.annotation.Value;

public interface SeasonNamesOnly {
    long getId();

    @Value("#{target.ruleset.game.shortName + ' ' + target.ruleset.shortName + ' S' + target.name}")
    String getName();
}
