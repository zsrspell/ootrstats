package com.ootrstats.ootrstats.racetime;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.Duration;
import java.time.Instant;

@Data
public class RacetimeRaceEntrant implements Serializable {
    private RacetimeUser user;
    private RacetimeTeam team;
    private RacetimeStatus status;

    @JsonProperty("finish_time")
    private Duration finishTime;

    @JsonProperty("finished_at")
    private Instant finishedAt;

    private int place;

    @JsonProperty("place_ordinal")
    private String placeOrdinal;

    private int score;

    @JsonProperty("score_change")
    private int scoreChange;

    private String comment;

    @JsonProperty("stream_live")
    private boolean streamLive;

    @JsonProperty("stream_override")
    private boolean streamOverride;

    public boolean hasComment() {
        return comment != null;
    }
}
