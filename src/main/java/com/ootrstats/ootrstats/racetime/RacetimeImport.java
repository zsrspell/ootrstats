package com.ootrstats.ootrstats.racetime;

import com.ootrstats.ootrstats.race.Race;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Entity
@Table(name = "racetime_imports")
public class RacetimeImport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "category_slug", length = 16, nullable = false)
    private String categorySlug;

    @Column(name = "race_slug", nullable = false)
    private String raceSlug;

    @Column(name = "checksum", length = 8, nullable = false)
    private String checksum;

    @Column(name ="recorded", nullable = false)
    private boolean recorded = false;

    @Column(name = "import_date", nullable = false)
    private OffsetDateTime importDate = OffsetDateTime.now();

    @ManyToOne(targetEntity = Race.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "race_id", referencedColumnName = "id")
    private Race race;

    @ManyToMany(targetEntity = RacetimeSpeedrunnerConflict.class, fetch = FetchType.EAGER)
    @JoinTable(name = "racetime_race_import_conflicts",
            joinColumns = @JoinColumn(name = "import_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "conflict_id", referencedColumnName = "id"))
    private Set<RacetimeSpeedrunnerConflict> conflicts = new HashSet<>();

    protected RacetimeImport() {
    }

    public RacetimeImport(@NonNull String categorySlug, @NonNull String raceSlug, @NonNull String checksum) {
        this.categorySlug = Objects.requireNonNull(categorySlug);
        this.raceSlug = Objects.requireNonNull(raceSlug);
        this.checksum = Objects.requireNonNull(checksum);
    }

    public Long getId() {
        return id;
    }

    @NonNull
    public String getCategorySlug() {
        return categorySlug;
    }

    public void setCategorySlug(@NonNull String categorySlug) {
        this.categorySlug = Objects.requireNonNull(categorySlug);
    }

    @NonNull
    public String getRaceSlug() {
        return raceSlug;
    }

    public void setRaceSlug(@NonNull String raceSlug) {
        this.raceSlug = Objects.requireNonNull(raceSlug);
    }

    @NonNull
    public String getChecksum() {
        return checksum;
    }

    public void setChecksum(@NonNull String checksum) {
        this.checksum = Objects.requireNonNull(checksum);
    }

    public boolean isRecorded() {
        return recorded;
    }

    public void setRecorded(boolean recorded) {
        this.recorded = recorded;
    }

    public OffsetDateTime getImportDate() {
        return importDate;
    }

    public void setImportDate(OffsetDateTime importDate) {
        this.importDate = Objects.requireNonNull(importDate);
    }

    public Optional<Race> getRace() {
        return Optional.ofNullable(race);
    }

    public void setRace(Race race) {
        this.race = race;
    }

    public Set<RacetimeSpeedrunnerConflict> getConflicts() {
        return conflicts;
    }

    public void setConflicts(Set<RacetimeSpeedrunnerConflict> conflicts) {
        this.conflicts = conflicts;
    }
}

