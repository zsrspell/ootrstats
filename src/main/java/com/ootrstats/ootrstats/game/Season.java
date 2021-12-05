package com.ootrstats.ootrstats.game;

import com.ootrstats.ootrstats.race.Race;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "seasons",
        uniqueConstraints = @UniqueConstraint(columnNames = {"ruleset_id", "name"}))
public class Season {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private long name;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ruleset_id", nullable = false)
    private Ruleset ruleset;

    @OneToMany(mappedBy = "season", fetch = FetchType.LAZY)
    private Set<Race> races = new HashSet<>();

    public Season() {
    }

    public Season(long name, @NonNull Ruleset ruleset) {
        this.name = name;
        this.ruleset = Objects.requireNonNull(ruleset);
    }

    public Long getId() {
        return id;
    }

    public long getName() {
        return name;
    }

    public void setName(long name) {
        this.name = name;
    }

    @NonNull
    public Ruleset getRuleset() {
        return ruleset;
    }

    public void setRuleset(@NonNull Ruleset ruleset) {
        this.ruleset = Objects.requireNonNull(ruleset);
    }

    @NonNull
    public Set<Race> getRaces() {
        return races;
    }

    public void setRaces(@NonNull Set<Race> races) {
        this.races = Objects.requireNonNull(races);
    }

    public String getFullName() {
        return String.format("%s S%d", ruleset.getName(), name);
    }
}
