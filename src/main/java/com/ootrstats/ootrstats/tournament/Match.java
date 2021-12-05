package com.ootrstats.ootrstats.tournament;

import com.ootrstats.ootrstats.race.Race;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "matches")
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "stage_id", nullable = false)
    private Stage stage;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "race_id", referencedColumnName = "id", nullable = false)
    private Race race;

    public Match() {
    }

    public Match(@NonNull Stage stage, @NonNull Race race) {
        this.stage = Objects.requireNonNull(stage);
        this.race = Objects.requireNonNull(race);
    }

    public Long getId() {
        return id;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Race getRace() {
        return race;
    }

    public void setRace(Race race) {
        this.race = race;
    }
}
