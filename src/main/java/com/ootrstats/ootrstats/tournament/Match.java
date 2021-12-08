package com.ootrstats.ootrstats.tournament;

import com.ootrstats.ootrstats.race.Race;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.Objects;
import java.util.Optional;

@Entity
@Table(name = "matches")
@NamedEntityGraph(
        name = "Match.subtypes",
        attributeNodes = {
                @NamedAttributeNode("groupMatch"),
                @NamedAttributeNode("bracketMatch"),
        }
)
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "stage_id", nullable = false)
    private Stage stage;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "race_id", referencedColumnName = "id")
    private Race race;

    @OneToOne(mappedBy = "match")
    private GroupMatch groupMatch;

    @OneToOne(mappedBy = "match")
    private BracketMatch bracketMatch;

    protected Match() {
    }

    public Match(@NonNull Stage stage) {
        this.stage = Objects.requireNonNull(stage);
    }

    public Match(@NonNull Stage stage, Race race) {
        this.stage = Objects.requireNonNull(stage);
        this.race = race;
    }

    public Long getId() {
        return id;
    }

    @NonNull
    public Stage getStage() {
        return stage;
    }

    public void setStage(@NonNull Stage stage) {
        this.stage = Objects.requireNonNull(stage);
    }

    @NonNull
    public Optional<Race> getRace() {
        return Optional.ofNullable(race);
    }

    public void setRace(Race race) {
        this.race = race;
    }
}
