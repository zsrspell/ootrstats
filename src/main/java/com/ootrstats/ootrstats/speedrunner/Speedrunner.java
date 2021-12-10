package com.ootrstats.ootrstats.speedrunner;

import com.ootrstats.ootrstats.race.Entrant;
import com.ootrstats.ootrstats.user.User;
import org.hibernate.annotations.NaturalId;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Entity
@Table(name = "speedrunners")
public class Speedrunner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @NaturalId
    @Column(name = "name", nullable = false, unique = true, length = 32)
    private String name;

    @Column(name = "country", length = 2)
    private String countryCode;

    @Column(name = "racetime_id", length = 32, unique = true)
    private String racetimeId;

    @Column(name = "racetime_name", length = 32)
    private String racetimeName;

    @Column(name = "twitch_channel", length = 32, unique = true)
    private String twitchChannel;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToMany(mappedBy = "members", fetch = FetchType.LAZY)
    private Set<Team> teams = new HashSet<>();

    @OneToMany(mappedBy = "speedrunner", fetch = FetchType.LAZY)
    private Set<Entrant> entries = new HashSet<>();

    public Speedrunner() {
    }

    public Speedrunner(@NonNull String name) {
        this.name = Objects.requireNonNull(name);
    }

    public Speedrunner(@NonNull String name, @NonNull String twitchChannel) {
        this.name = Objects.requireNonNull(name);
        this.twitchChannel = Objects.requireNonNull(twitchChannel);
    }

    public Speedrunner(@NonNull String name, @NonNull String racetimeId, @NonNull String racetimeName) {
        this.name = Objects.requireNonNull(name);
        this.racetimeId = Objects.requireNonNull(racetimeId);
        this.racetimeName = Objects.requireNonNull(racetimeName);
    }

    public Speedrunner(@NonNull String name,
                       @NonNull String racetimeId,
                       @NonNull String racetimeName,
                       String twitchChannel) {
        this.name = Objects.requireNonNull(name);
        this.racetimeId = Objects.requireNonNull(racetimeId);
        this.racetimeName = Objects.requireNonNull(racetimeName);
        this.twitchChannel = twitchChannel;
    }

    public Long getId() {
        return id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = Objects.requireNonNull(name);
    }

    @NonNull
    public Optional<String> getCountryCode() {
        return Optional.ofNullable(countryCode);
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    @NonNull
    public Optional<String> getRacetimeId() {
        return Optional.ofNullable(racetimeId);
    }

    public void setRacetimeId(String racetimeId) {
        this.racetimeId = racetimeId;
    }

    @NonNull
    public Optional<String> getRacetimeName() {
        return Optional.ofNullable(racetimeName);
    }

    public void setRacetimeName(String racetimeName) {
        this.racetimeName = racetimeName;
    }

    @NonNull
    public Optional<String> getTwitchChannel() {
        return Optional.ofNullable(twitchChannel);
    }

    public void setTwitchChannel(String twitchChannel) {
        this.twitchChannel = twitchChannel;
    }

    @NonNull
    public Optional<User> getUser() {
        return Optional.ofNullable(user);
    }

    public void setUser(User user) {
        this.user = user;
    }

    @NonNull
    public Set<Team> getTeams() {
        return teams;
    }

    public void setTeams(@NonNull Set<Team> teams) {
        this.teams = Objects.requireNonNull(teams);
    }

    public void joinTeam(@NonNull Team team) {
        Objects.requireNonNull(team);
        this.teams.add(team);
        team.getMembers().add(this);
    }

    public void leaveTeam(@NonNull Team team) {
        Objects.requireNonNull(team);
        this.teams.remove(team);
        team.getMembers().remove(this);
    }

    @NonNull
    public Set<Entrant> getEntries() {
        return entries;
    }

    public void setEntries(@NonNull Set<Entrant> entries) {
        this.entries = Objects.requireNonNull(entries);
    }
}
