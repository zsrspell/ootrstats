package com.ootrstats.ootrstats.tournament;

import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "bracket_stages")
public class BracketStage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(name = "stage_id", referencedColumnName = "id", unique = true, updatable = false)
    private Stage stage;

    protected BracketStage() {
    }

    public BracketStage(@NonNull Stage stage) {
        this.stage = Objects.requireNonNull(stage);
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
}
