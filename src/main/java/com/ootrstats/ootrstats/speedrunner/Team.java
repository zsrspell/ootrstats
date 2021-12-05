package com.ootrstats.ootrstats.speedrunner;

import com.ootrstats.ootrstats.race.Entrant;
import org.hibernate.annotations.NaturalId;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "teams")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @NaturalId
    @Column(name = "name", unique = true, nullable = false, length = 32)
    private String name;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "team_members",
            joinColumns = @JoinColumn(name = "speedrunner_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "team_id", referencedColumnName = "id"))
    private Set<Speedrunner> members = new HashSet<>();

    @OneToMany(mappedBy = "team", fetch = FetchType.LAZY)
    private Set<Entrant> entries = new HashSet<>();

    public Team() {
    }

    public Team(@NonNull String name) {
        this.name = Objects.requireNonNull(name);
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
    public Set<Speedrunner> getMembers() {
        return members;
    }

    public void setMembers(@NonNull Set<Speedrunner> members) {
        this.members = Objects.requireNonNull(members);
    }

    public void addMember(@NonNull Speedrunner speedrunner) {
        Objects.requireNonNull(speedrunner);
        this.members.add(speedrunner);
        speedrunner.getTeams().add(this);
    }

    public void removeMember(@NonNull Speedrunner speedrunner) {
        Objects.requireNonNull(speedrunner);
        this.members.remove(speedrunner);
        speedrunner.getTeams().remove(this);
    }

    @NonNull
    public Set<Entrant> getEntries() {
        return entries;
    }

    public void setEntries(@NonNull Set<Entrant> entries) {
        this.entries = Objects.requireNonNull(entries);
    }
}
