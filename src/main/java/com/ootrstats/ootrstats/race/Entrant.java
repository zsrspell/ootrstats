package com.ootrstats.ootrstats.race;

import com.ootrstats.ootrstats.speedrunner.Speedrunner;
import com.ootrstats.ootrstats.speedrunner.Team;
import com.vladmihalcea.hibernate.type.interval.PostgreSQLIntervalType;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.TypeDef;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.time.Duration;
import java.util.Objects;
import java.util.Optional;

@Entity
@Table(name = "entrants")
@TypeDef(
        typeClass = PostgreSQLIntervalType.class,
        defaultForType = Duration.class
)
public class Entrant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "finish_time", columnDefinition = "interval")
    private Duration finishTime;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "race_id")
    private Race race;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(name = "speedrunner_entrants",
            joinColumns = @JoinColumn(name = "entrant_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "speedrunner_id", referencedColumnName = "id"))
    private Speedrunner speedrunner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(name = "team_entrants",
            joinColumns = @JoinColumn(name = "entrant_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "team_id", referencedColumnName = "id"))
    private Team team;

    protected Entrant() {
    }

    public Entrant(@NonNull Race race, @NonNull String name, @NonNull Speedrunner speedrunner, Duration finishTime) {
        this.race = Objects.requireNonNull(race);
        this.name = Objects.requireNonNull(name);
        this.speedrunner = Objects.requireNonNull(speedrunner);
        this.finishTime = finishTime;
    }

    public Entrant(@NonNull Race race, @NonNull String name, @NonNull Team team, Duration finishTime) {
        this.race = Objects.requireNonNull(race);
        this.name = Objects.requireNonNull(name);
        this.team = Objects.requireNonNull(team);
        this.finishTime = finishTime;
    }

    @NonNull
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
    public Optional<Duration> getFinishTime() {
        return Optional.ofNullable(finishTime);
    }

    public boolean hasForfeited() {
        return finishTime == null;
    }

    public void setFinishTime(Duration finishTime) {
        this.finishTime = finishTime;
    }

    @NonNull
    public Race getRace() {
        return race;
    }

    public void setRace(@NonNull Race race) {
        this.race = Objects.requireNonNull(race);
    }

    @NonNull
    public Optional<Speedrunner> getSpeedrunner() {
        return Optional.ofNullable(speedrunner);
    }

    @NonNull
    public Optional<Team> getTeam() {
        return Optional.ofNullable(team);
    }
}
