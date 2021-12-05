package com.ootrstats.ootrstats.race.projections;

import com.ootrstats.ootrstats.speedrunner.projections.SpeedrunnerNameOnly;
import com.ootrstats.ootrstats.speedrunner.projections.TeamNameOnly;
import lombok.Data;

import java.time.Duration;
import java.util.Optional;

public interface EntrantInfo {
    String getName();

    Optional<Duration> getFinishTime();

    SpeedrunnerNameOnly getSpeedrunner();

    TeamNameOnly getTeam();
}
