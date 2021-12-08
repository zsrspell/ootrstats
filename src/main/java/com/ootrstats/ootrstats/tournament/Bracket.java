package com.ootrstats.ootrstats.tournament;

import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.Objects;
import java.util.Optional;

@Entity
@Table(name = "brackets")
public class Bracket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "name", length = 32, nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "bracket_stage_id", referencedColumnName = "id", nullable = false)
    private BracketStage stage;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "relegation_bracket_id", referencedColumnName = "id")
    private Bracket relegationBracket;

    protected Bracket() {
    }

    public Bracket(@NonNull String name, @NonNull BracketStage stage) {
        this.name = Objects.requireNonNull(name);
        this.stage = Objects.requireNonNull(stage);
    }

    public Long getId() {
        return id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public BracketStage getStage() {
        return stage;
    }

    public void setStage(@NonNull BracketStage stage) {
        this.stage = stage;
    }

    @NonNull
    public Optional<Bracket> getRelegationBracket() {
        return Optional.ofNullable(relegationBracket);
    }

    public void setRelegationBracket(Bracket relegationBracket) {
        this.relegationBracket = relegationBracket;
    }
}
