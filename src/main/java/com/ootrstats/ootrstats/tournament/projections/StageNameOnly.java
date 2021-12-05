package com.ootrstats.ootrstats.tournament.projections;

import org.springframework.beans.factory.annotation.Value;

public interface StageNameOnly {
    Long getId();

    @Value("#{target.tournament.shortName + ' ' + target.name}")
    String getName();
}
