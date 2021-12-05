package com.ootrstats.ootrstats.race.projections;

import java.time.Duration;
import java.time.OffsetDateTime;

public interface RaceInfo {
    Long getId();
    String getName();
    String getGameName();
    String getRulesetName();
    String getTournamentName();
    String getStageName();
    OffsetDateTime getStartedAt();
    long getEntrantCount();
    Duration getFinishTime();
}
