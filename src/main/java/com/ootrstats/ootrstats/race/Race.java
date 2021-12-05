package com.ootrstats.ootrstats.race;

import com.ootrstats.ootrstats.game.Season;
import com.ootrstats.ootrstats.tournament.Match;
import org.hibernate.annotations.NaturalId;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Entity
@Table(name = "races")
public class Race {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @NaturalId
    @Column(name = "name", nullable = false, length = 128)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "started_at", nullable = false)
    private OffsetDateTime startedAt;

    @OneToMany(mappedBy = "race", fetch = FetchType.LAZY)
    private Set<Entrant> entrants = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "season_id")
    private Season season;

    @OneToOne(mappedBy = "race", fetch = FetchType.LAZY)
    private Match match;

    public Race() {
    }


    public Race(@NonNull String name, @NonNull OffsetDateTime startedAt, @NonNull Season season) {
        this.name = Objects.requireNonNull(name);
        this.startedAt = Objects.requireNonNull(startedAt);
        this.season = Objects.requireNonNull(season);
    }

    public Race(@NonNull String name, String description, @NonNull Season season, @NonNull OffsetDateTime startedAt) {
        this.name = Objects.requireNonNull(name);
        this.description = description;
        this.season = Objects.requireNonNull(season);
        this.startedAt = Objects.requireNonNull(startedAt);
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
    public Optional<String> getDescription() {
        return Optional.ofNullable(description);
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NonNull
    public OffsetDateTime getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(@NonNull OffsetDateTime startedAt) {
        this.startedAt = Objects.requireNonNull(startedAt);
    }

    @NonNull
    public Set<Entrant> getEntrants() {
        return entrants;
    }

    public void setEntrants(@NonNull Set<Entrant> entrants) {
        this.entrants = Objects.requireNonNull(entrants);
    }

    @NonNull
    public Optional<Season> getSeason() {
        return Optional.ofNullable(season);
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    @NonNull
    public Optional<Match> getMatch() {
        return Optional.ofNullable(match);
    }

    public void setMatch(Match match) {
        this.match = match;
    }
}
