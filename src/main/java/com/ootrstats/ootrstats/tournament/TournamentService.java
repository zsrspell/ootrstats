package com.ootrstats.ootrstats.tournament;

import com.ootrstats.ootrstats.race.Race;
import com.ootrstats.ootrstats.tournament.projections.StageNameOnly;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TournamentService {
    private final TournamentRepository tournaments;
    private final StageRepository stages;
    private final MatchRepository matches;

    public TournamentService(TournamentRepository tournaments, StageRepository stages, MatchRepository matches) {
        this.tournaments = tournaments;
        this.stages = stages;
        this.matches = matches;
    }

    public Optional<Stage> findStageById(long id) {
        return stages.findById(id);
    }

    public Iterable<StageNameOnly> findAllStages() {
        return stages.findAll(StageNameOnly.class);
    }

    public Match createMatch(Stage stage, Race race) {
        return matches.save(new Match(stage, race));
    }
}
