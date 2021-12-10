package com.ootrstats.ootrstats.racetime.api;

import lombok.Data;

import java.io.Serializable;

@Data
public class RacetimeRaceGoal  implements Serializable {
    private String name;
    private boolean custom;
}
