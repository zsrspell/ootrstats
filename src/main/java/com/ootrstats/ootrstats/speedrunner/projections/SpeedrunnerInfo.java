package com.ootrstats.ootrstats.speedrunner.projections;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class SpeedrunnerInfo {
    private String name;
    private String countryCode;
    private long racesEntered;
    private OffsetDateTime lastRaceDate;

    public SpeedrunnerInfo(String name, String countryCode, long racesEntered, OffsetDateTime lastRaceDate) {
        this.name = name;
        this.countryCode = countryCode;
        this.racesEntered = racesEntered;
        this.lastRaceDate = lastRaceDate;
    }
}
