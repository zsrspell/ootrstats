package com.ootrstats.ootrstats.racetime;

import com.ootrstats.ootrstats.speedrunner.Speedrunner;
import com.ootrstats.ootrstats.user.User;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "racetime_speedrunner_conflicts")
public class RacetimeSpeedrunnerConflict {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "racetime_id", length = 32, unique = true, nullable = false)
    private String racetimeId;

    @Column(name = "name", length = 64, nullable = false)
    private String name;

    @Column(name = "full_name", length = 64, nullable = false)
    private String fullName;

    @Column(name = "twitch_channel", length = 32)
    private String twitchChannel;

    @OneToOne(targetEntity = Speedrunner.class, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "speedrunner_id", referencedColumnName = "id", nullable = false)
    private Speedrunner speedrunner;

    @ManyToMany(targetEntity = RacetimeImport.class, mappedBy = "conflicts", fetch = FetchType.EAGER)
    private Set<RacetimeImport> imports = new HashSet<>();

    protected RacetimeSpeedrunnerConflict() {
    }

    public RacetimeSpeedrunnerConflict(String racetimeId, String name, String fullName, String twitchChannel, Speedrunner speedrunner) {
        this.racetimeId = Objects.requireNonNull(racetimeId);
        this.name = Objects.requireNonNull(name);
        this.fullName = Objects.requireNonNull(fullName);
        this.twitchChannel = twitchChannel;
        this.speedrunner = Objects.requireNonNull(speedrunner);
    }

    public Long getId() {
        return id;
    }

    public String getRacetimeId() {
        return racetimeId;
    }

    public void setRacetimeId(String racetimeId) {
        this.racetimeId = racetimeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getTwitchChannel() {
        return twitchChannel;
    }

    public void setTwitchChannel(String twitchChannel) {
        this.twitchChannel = twitchChannel;
    }

    public Speedrunner getSpeedrunner() {
        return speedrunner;
    }

    public void setSpeedrunner(Speedrunner speedrunner) {
        this.speedrunner = speedrunner;
    }

    public Set<RacetimeImport> getImports() {
        return imports;
    }

    public void setImports(Set<RacetimeImport> imports) {
        this.imports = imports;
    }
}
