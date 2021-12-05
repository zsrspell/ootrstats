package com.ootrstats.ootrstats.racetime;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class RacetimeCategory implements Serializable {
    private String name;

    @JsonProperty("short_name")
    private String shortName;

    private String slug;
    private String url;

    @JsonProperty("data_url")
    private String dataUrl;

    private String image;
}
