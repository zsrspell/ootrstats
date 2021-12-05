package com.ootrstats.ootrstats.racetime;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class RacetimeUser implements Serializable {
    private String id;
    private String name;
    private String discriminator;
    private String url;
    private String avatar;
    private String pronouns;

    @JsonProperty("twitch_name")
    private String twitchName;

    @JsonProperty("twitch_display_name")
    private String twitchDisplayName;

    @JsonProperty("twitch_channel")
    private String twitchChannel;

    @JsonProperty("can_moderate")
    private boolean moderator;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private String flair;

    private RacetimeUserRaceStats stats;
    private List<RacetimeTeam> teams;

    public String getFullName() {
        if (discriminator != null) {
            return String.format("%s#%s", name, discriminator);
        }
        return name;
    }

    public Set<String> getFlairs() {
        if (flair != null) {
            return new HashSet<>(Arrays.asList(flair.split(" ")));
        }
        return new HashSet<>();
    }
}
