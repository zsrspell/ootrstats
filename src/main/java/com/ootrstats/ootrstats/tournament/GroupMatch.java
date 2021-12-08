package com.ootrstats.ootrstats.tournament;

import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "group_matches")
public class GroupMatch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "group_name", length = 2, nullable = false)
    private String groupName;

    @OneToOne(optional = false)
    @JoinColumn(name = "match_id", referencedColumnName = "id", nullable = false, unique = true, updatable = false)
    private Match match;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "group_stage_id", referencedColumnName = "id", unique = true)
    private GroupStage groupStage;

    protected GroupMatch() {
    }

    public GroupMatch(@NonNull String groupName, @NonNull Match match, @NonNull GroupStage groupStage) {
        this.groupName = Objects.requireNonNull(groupName);
        this.match = Objects.requireNonNull(match);
        this.groupStage = Objects.requireNonNull(groupStage);
    }

    public Long getId() {
        return id;
    }

    @NonNull
    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(@NonNull String groupName) {
        this.groupName = Objects.requireNonNull(groupName);
    }

    @NonNull
    public Match getMatch() {
        return match;
    }

    @NonNull
    public GroupStage getGroupStage() {
        return groupStage;
    }

    public void setGroupStage(@NonNull GroupStage groupStage) {
        this.groupStage = Objects.requireNonNull(groupStage);
    }
}
