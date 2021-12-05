package com.ootrstats.ootrstats.tournament;

import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "stages")
public class Stage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "slug", nullable = false)
    private String slug;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tournament_id", nullable = false)
    private Tournament tournament;

    public Stage() {
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
}
