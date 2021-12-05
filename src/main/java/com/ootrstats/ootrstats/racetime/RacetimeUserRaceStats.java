package com.ootrstats.ootrstats.racetime;

import lombok.Data;

import java.io.Serializable;

@Data
public class RacetimeUserRaceStats implements Serializable {
    private int joined;
    private int first;
    private int second;
    private int third;
    private int forfeits;
}
