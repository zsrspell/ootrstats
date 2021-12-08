package com.ootrstats.ootrstats.tournament;

import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(
        name = "stages",
        uniqueConstraints = @UniqueConstraint(columnNames = {"tournament_id", "slug"})
)
@NamedEntityGraph(
        name = "Stage.subtypes",
        attributeNodes = {
                @NamedAttributeNode("qualifierStage"),
                @NamedAttributeNode("groupStage"),
                @NamedAttributeNode("bracketStage"),
        }
)
public class Stage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "name", length = 16, nullable = false)
    private String name;

    @Column(name = "slug", length = 8, nullable = false)
    private String slug;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tournament_id", referencedColumnName = "id", nullable = false)
    private Tournament tournament;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "stage")
    private Set<Match> matches = new HashSet<>();

    @OneToOne(mappedBy = "stage")
    private QualifierStage qualifierStage;

    @OneToOne(mappedBy = "stage")
    private GroupStage groupStage;

    @OneToOne(mappedBy = "stage")
    private BracketStage bracketStage;

    protected Stage() {
    }

    public Stage(@NonNull String name, @NonNull String slug, @NonNull Tournament tournament) {
        this.name = Objects.requireNonNull(name);
        this.slug = Objects.requireNonNull(slug);
        this.tournament = Objects.requireNonNull(tournament);
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
    public String getSlug() {
        return slug;
    }

    public void setSlug(@NonNull String slug) {
        this.slug = Objects.requireNonNull(slug);
    }

    @NonNull
    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(@NonNull Tournament tournament) {
        this.tournament = Objects.requireNonNull(tournament);
    }

    @NonNull
    public Set<Match> getMatches() {
        return matches;
    }

    public void setMatches(@NonNull Set<Match> matches) {
        this.matches = matches;
    }
}
