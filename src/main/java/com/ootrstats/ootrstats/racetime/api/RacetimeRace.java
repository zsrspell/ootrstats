package com.ootrstats.ootrstats.racetime.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.Instant;

@Data
public class RacetimeRace implements Serializable {
    private String name;
    private RacetimeStatus status;
    private String url;

    @JsonProperty("data_url")
    private String dataUrl;

    private RacetimeRaceGoal goal;
    private String info;

    @JsonProperty("entrants_count")
    private long entrantCount;

    @JsonProperty("entrants_count_finished")
    private long entrantCountFinished;

    @JsonProperty("entrants_count_inactive")
    private long entrantCountUnfinished;

    @JsonProperty("opened_at")
    private Instant openedAt;

    @JsonProperty("started_at")
    private Instant startedAt;

    @JsonProperty("ended_at")
    private Instant endedAt;

    @JsonProperty("cancelled_at")
    private Instant cancelledAt;

    private boolean recordable;
    private boolean recorded;

    public boolean isInProgress() {
        return status.getValue().equals(RacetimeStatus.STATUS_IN_PROGRESS);
    }

    public boolean isFinished() {
        return status.getValue().equals(RacetimeStatus.STATUS_FINISHED);
    }

    public boolean isImportable() {
        return isFinished() && isRecorded();
    }
}
