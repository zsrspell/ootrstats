package com.ootrstats.ootrstats.racetime;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;

@Data
public class RacetimeRace implements Serializable {
    public static final String STATUS_IN_PROGRESS = "in_progress";
    public static final String STATUS_FINISHED = "finished";

    private int version;
    private String name;
    private String slug;
    private RacetimeStatus status;
    private String url;

    @JsonProperty("data_url")
    private String dataUrl;

    @JsonProperty("websocket_url")
    private String websocketUrl;

    @JsonProperty("websocket_bot_url")
    private String websocketBotUrl;

    @JsonProperty("websocket_oauth_url")
    private String websocketOAuthUrl;

    private RacetimeCategory category;
    private RacetimeRaceGoal goal;
    private String info;

    @JsonProperty("team_race")
    private boolean teamRace;

    @JsonProperty("entrants_count")
    private int entrantsCount;

    @JsonProperty("entrants_count_finished")
    private int entrantsCountFinished;

    @JsonProperty("entrants_count_inactive")
    private int entrantsCountInactive;

    private Iterable<RacetimeRaceEntrant> entrants = new ArrayList<>();

    @JsonProperty("opened_at")
    private Instant openedAt;

    @JsonProperty("start_delay")
    private Duration startDelay;

    @JsonProperty("started_at")
    private Instant startedAt;

    @JsonProperty("ended_at")
    private Instant endedAt;

    @JsonProperty("cancelled_at")
    private Instant cancelledAt;

    private boolean unlisted;

    @JsonProperty("time_limit")
    private Duration timeLimit;

    @JsonProperty("opened_by")
    private RacetimeUser openedBy;

    private Iterable<RacetimeUser> monitors = new ArrayList<>();
    private boolean recordable;
    private boolean recorded;

    @JsonProperty("recorded_by")
    private RacetimeUser recordedBy;

    @JsonProperty("allow_comments")
    private boolean allowComments;

    @JsonProperty("hide_comments")
    private boolean hideComments;

    @JsonProperty("allow_prerace_chat")
    private boolean preRaceChatAllowed;

    @JsonProperty("allow_midrace_chat")
    private boolean midRaceChatAllowed;

    @JsonProperty("allow_non_entrant_chat")
    private boolean nonEntrantChatAllowed;

    @JsonProperty("chat_message_delay")
    private Duration chatMessageDelay;

    public boolean isInProgress() {
        return status.getValue().equals(STATUS_IN_PROGRESS);
    }

    public boolean isFinished() {
        return status.getValue().equals(STATUS_FINISHED);
    }
}
