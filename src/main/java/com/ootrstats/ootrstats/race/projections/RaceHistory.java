package com.ootrstats.ootrstats.race.projections;

import java.time.Duration;
import java.time.OffsetDateTime;

public interface RaceHistory {
    Long getId();
    String getName();
    OffsetDateTime getStartedAt();
    Duration getFinishTime();
}
