package com.ootrstats.ootrstats.racetime.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class RacetimeTeam implements Serializable {
    private String name;
    private String slug;
    private boolean formal;
    private String url;

    @JsonProperty("data_url")
    private String dataUrl;

    private String avatar;
}
