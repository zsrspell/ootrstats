package com.ootrstats.ootrstats.tournament;

import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "qualifier_stages")
public class QualifierStage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(name = "stage_id", referencedColumnName = "id", unique = true, updatable = false)
    private Stage stage;

    protected QualifierStage() {
    }

    public QualifierStage(@NonNull Stage stage) {
        this.stage = Objects.requireNonNull(stage);
    }

    public Long getId() {
        return id;
    }

    @NonNull
    public Stage getStage() {
        return stage;
    }
}
