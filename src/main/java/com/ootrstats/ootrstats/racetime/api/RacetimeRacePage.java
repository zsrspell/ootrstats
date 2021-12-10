package com.ootrstats.ootrstats.racetime.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RacetimeRacePage {
    private long count;

    @JsonProperty("num_pages")
    private long numPages;

    private Iterable<RacetimeRace> races;
}
