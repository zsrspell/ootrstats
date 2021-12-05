package com.ootrstats.ootrstats.game;

import org.hibernate.annotations.NaturalId;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "games")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "name", length = 64, nullable = false)
    private String name;

    @Column(name = "short_name", length = 8, nullable = false)
    private String shortName;

    @NaturalId
    @Column(name = "slug", length = 8, nullable = false, unique = true)
    private String slug;

    @OneToMany(mappedBy = "game", fetch = FetchType.LAZY)
    private Set<Ruleset> rulesets = new HashSet<>();

    public Game() {
    }

    public Game(@NonNull String name, @NonNull String shortName, @NonNull String slug) {
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
    public Set<Ruleset> getRulesets() {
        return rulesets;
    }

    public void setRulesets(@NonNull Set<Ruleset> rulesets) {
        this.rulesets = Objects.requireNonNull(rulesets);
    }
}
