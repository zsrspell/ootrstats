package com.ootrstats.ootrstats.game;

import org.hibernate.annotations.NaturalId;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "rulesets",
        uniqueConstraints = @UniqueConstraint(columnNames = {"game_id", "slug"}))
public class Ruleset {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = 32)
    private String name;

    @Column(name = "short_name", nullable = false, length = 8)
    private String shortName;

    @Column(name = "slug", nullable = false, length = 8)
    private String slug;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    @OneToMany(mappedBy = "ruleset", fetch = FetchType.LAZY)
    private Set<Season> seasons;

    public Ruleset() {
    }

    public Ruleset(@NonNull String name, @NonNull String shortName, @NonNull String slug, @NonNull Game game) {
        this.name = Objects.requireNonNull(name);
        this.shortName = Objects.requireNonNull(shortName);
        this.slug = Objects.requireNonNull(slug);
        this.game = Objects.requireNonNull(game);
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
    public Game getGame() {
        return game;
    }

    public void setGame(@NonNull Game game) {
        this.game = Objects.requireNonNull(game);
    }
}
