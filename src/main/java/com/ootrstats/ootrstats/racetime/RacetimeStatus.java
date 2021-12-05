package com.ootrstats.ootrstats.racetime;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class RacetimeStatus implements Serializable {
    private String value;

    @JsonProperty("verbose_value")
    private String verboseValue;

    @JsonProperty("help_text")
    private String helpText;
}
