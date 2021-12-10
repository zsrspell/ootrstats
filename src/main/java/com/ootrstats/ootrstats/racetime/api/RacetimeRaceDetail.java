package com.ootrstats.ootrstats.racetime.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.Duration;
import java.util.ArrayList;

@Data
@EqualsAndHashCode(callSuper = true)
public class RacetimeRaceDetail extends RacetimeRace implements Serializable {
    private int version;
    private String slug;

    @JsonProperty("websocket_url")
    private String websocketUrl;

    @JsonProperty("websocket_bot_url")
    private String websocketBotUrl;

    @JsonProperty("websocket_oauth_url")
    private String websocketOAuthUrl;

    private RacetimeCategory category;

    @JsonProperty("team_race")
    private boolean teamRace;

    private Iterable<RacetimeRaceEntrant> entrants = new ArrayList<>();

    @JsonProperty("start_delay")
    private Duration startDelay;

    private boolean unlisted;

    @JsonProperty("opened_by")
    private RacetimeUser openedBy;

    private Iterable<RacetimeUser> monitors = new ArrayList<>();

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
}
