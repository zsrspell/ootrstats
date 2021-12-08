package com.ootrstats.ootrstats.tournament;

import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "group_stages")
public class GroupStage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(name = "stage_id", referencedColumnName = "id", unique = true, updatable = false)
    private Stage stage;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "groupStage")
    private Set<GroupMatch> matches = new HashSet<>();

    protected GroupStage() {
    }

    public GroupStage(@NonNull Stage stage) {
        this.stage = Objects.requireNonNull(stage);
    }

    public Long getId() {
        return id;
    }

    @NonNull
    public Stage getStage() {
        return stage;
    }

    @NonNull
    public Set<GroupMatch> getMatches() {
        return matches;
    }

    public void setMatches(@NonNull Set<GroupMatch> matches) {
        this.matches = Objects.requireNonNull(matches);
    }
}
