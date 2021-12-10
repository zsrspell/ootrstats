package com.ootrstats.ootrstats.racetime.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class RacetimeStatus implements Serializable {
    public static final String STATUS_IN_PROGRESS = "in_progress";
    public static final String STATUS_FINISHED = "finished";

    private String value;

    @JsonProperty("verbose_value")
    private String verboseValue;

    @JsonProperty("help_text")
    private String helpText;
}
