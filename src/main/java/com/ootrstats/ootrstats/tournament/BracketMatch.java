package com.ootrstats.ootrstats.tournament;

import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "bracket_matches",
        uniqueConstraints = @UniqueConstraint(columnNames = {"bracket_id", "match_index"}))
public class BracketMatch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "match_index", nullable = false)
    private int matchIndex;

    @OneToOne(optional = false)
    @JoinColumn(name = "match_id", referencedColumnName = "id", nullable = false, unique = true, updatable = false)
    private Match match;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "bracket_id", referencedColumnName = "id", nullable = false)
    private Bracket bracket;

    protected BracketMatch() {
    }

    public BracketMatch(int matchIndex, @NonNull Match match, @NonNull Bracket bracket) {
        this.matchIndex = matchIndex;
        this.match = Objects.requireNonNull(match);
        this.bracket = Objects.requireNonNull(bracket);
    }

    public Long getId() {
        return id;
    }

    public int getMatchIndex() {
        return matchIndex;
    }

    public void setMatchIndex(int matchIndex) {
        this.matchIndex = matchIndex;
    }

    @NonNull
    public Match getMatch() {
        return match;
    }

    @NonNull
    public Bracket getBracket() {
        return bracket;
    }

    public void setBracket(@NonNull Bracket bracket) {
        this.bracket = Objects.requireNonNull(bracket);
    }
}
