package com.ootrstats.ootrstats.tournament;

import org.hibernate.annotations.NaturalId;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "tournaments")
public class Tournament {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = 64)
    private String name;

    @Column(name = "short_name", nullable = false, length = 16)
    private String shortName;

    @NaturalId
    @Column(name = "slug", nullable = false, unique = true, length = 16)
    private String slug;

    @OneToMany(mappedBy = "tournament")
    private Set<Stage> stages = new HashSet<>();

    public Tournament() {
    }

    public Tournament(@NonNull String name, @NonNull String shortName, @NonNull String slug) {
        this.name = Objects.requireNonNull(name);
        this.shortName = Objects.requireNonNull(shortName);
        this.slug = Objects.requireNonNull(slug);
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
    public String getShortName() {
        return shortName;
    }

    public void setShortName(@NonNull String shortName) {
        this.shortName = Objects.requireNonNull(shortName);
    }

    @NonNull
    public String getSlug() {
        return slug;
    }

    public void setSlug(@NonNull String slug) {
        this.slug = Objects.requireNonNull(slug);
    }

    @NonNull
    public Set<Stage> getStages() {
        return stages;
    }

    public void setStages(@NonNull Set<Stage> stages) {
        this.stages = Objects.requireNonNull(stages);
    }
}
