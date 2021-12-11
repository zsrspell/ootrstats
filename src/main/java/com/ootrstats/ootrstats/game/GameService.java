package com.ootrstats.ootrstats.game;

import com.ootrstats.ootrstats.game.projections.SeasonNamesOnly;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GameService {
    private final GameRepository games;
    private final RulesetRepository rulesets;
    private final SeasonRepository seasons;

    public GameService(GameRepository games, RulesetRepository rulesets, SeasonRepository seasons) {
        this.games = games;
        this.rulesets = rulesets;
        this.seasons = seasons;
    }

    public Optional<Season> findSeasonById(long id) {
        return seasons.findById(id);
    }

    public Iterable<SeasonNamesOnly> findAllSeasonNames() {
        return seasons.findAllSeasonNames();
    }

    public Optional<Season> findSeason(String gameSlug, String rulesetSlug, int season) {
        return seasons.findByNameAndRulesetSlugAndRulesetGameSlug(season, rulesetSlug, gameSlug);
    }

    public Optional<Ruleset> findRuleset(String gameSlug, String rulesetSlug) {
        return rulesets.findByGameSlugAndSlug(gameSlug, rulesetSlug);
    }
}
