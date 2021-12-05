package com.ootrstats.ootrstats.race;

import com.ootrstats.ootrstats.game.GameService;
import com.ootrstats.ootrstats.race.forms.RacetimeImportForm;
import com.ootrstats.ootrstats.racetime.RacetimeService;
import com.ootrstats.ootrstats.racetime.RacetimeUser;
import com.ootrstats.ootrstats.racetime.exceptions.RacetimeStatusException;
import com.ootrstats.ootrstats.speedrunner.Speedrunner;
import com.ootrstats.ootrstats.speedrunner.SpeedrunnerService;
import com.ootrstats.ootrstats.speedrunner.exceptions.ImportConflictException;
import com.ootrstats.ootrstats.tournament.Match;
import com.ootrstats.ootrstats.tournament.TournamentService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/races")
public class RaceController {
    private final RaceService raceService;
    private final RacetimeService racetimeService;
    private final SpeedrunnerService speedrunnerService;
    private final GameService gameService;
    private final TournamentService tournamentService;

    public RaceController(RaceService raceService, RacetimeService racetimeService, SpeedrunnerService speedrunnerService, GameService gameService, TournamentService tournamentService) {
        this.raceService = raceService;
        this.racetimeService = racetimeService;
        this.speedrunnerService = speedrunnerService;
        this.gameService = gameService;
        this.tournamentService = tournamentService;
    }

    @GetMapping
    public String raceList(@PageableDefault(sort = "startedAt", direction = Sort.Direction.DESC, size = 50) Pageable pageable,
                           Model model) {
        model.addAttribute("races", raceService.findAll(pageable));
        return "race/list";
    }

    @GetMapping("/{id}")
    public String raceDetail(@PathVariable long id, Model model) {
        var race = raceService.findById(id).orElseThrow();
        var entrants = raceService.findAllEntrantsByRace(race);

        model.addAttribute("race", race);
        model.addAttribute("entrants", entrants);
        return "race/detail";
    }

    @GetMapping("/import/racetime")
    public String importRacetimeRaceForm(Model model) {
        var seasons = gameService.findAllSeasonNames();
        var stages = tournamentService.findAllStages();

        if (!model.containsAttribute("form")) {
            model.addAttribute("form", new RacetimeImportForm());
        }

        model.addAttribute("seasons", seasons);
        model.addAttribute("stages", stages);
        return "race/racetime_import_form";
    }


    @PostMapping("/import/racetime")
    public String importRacetimeRace(@ModelAttribute("form") @Valid RacetimeImportForm form,
                                     BindingResult result, Model model) {
        model.addAttribute("form", form);

        // Fuck pattern matching validators! Why does it work here but not as a validator? Argh!
        var pattern = Pattern.compile("^[a-z0-9-]+/([a-z0-9]+-){2}[0-9]{4}$");
        if (!pattern.matcher(form.getSlug()).matches()) {
            result.addError(new ObjectError("slug", "Not a valid Racetime slug."));
            return importRacetimeRaceForm(model);
        }

        var seasonOptional = gameService.findSeasonById(form.getSeasonId());
        if (seasonOptional.isEmpty()) {
            result.addError(new ObjectError("seasonId", "This season does not exist."));
            return importRacetimeRaceForm(model);
        }

        var category = form.getSlug().split("/", 2)[0];
        var slug = form.getSlug().split("/", 2)[1];
        var raceOptional = racetimeService.getRace(category, slug);
        if (raceOptional.isEmpty()) {
            result.addError(new ObjectError("globalError", "No race with this slug can be found"));
            return importRacetimeRaceForm(model);
        }

        var race = raceOptional.get();
        if (!race.isFinished()) {
            result.addError(new ObjectError("globalError", "This race is in progress and cannot be imported yet."));
            return importRacetimeRaceForm(model);
        }

        var successfulImports = new ArrayList<Speedrunner>();
        var conflicts = new ArrayList<Map.Entry<RacetimeUser, Speedrunner>>();

        // Before anything else, we want to import the speedrunners on this list. Collect any failures and ask
        // for user input to resolve conflicts.
        for (var entrant : race.getEntrants()) {
            var currentUser = entrant.getUser();
            try {
                var speedrunner = speedrunnerService.importRacetimeUser(currentUser);
                successfulImports.add(speedrunner);
            } catch (ImportConflictException e) {
                conflicts.add(Map.entry(currentUser, e.getSpeedrunner()));
            }
        }

        model.addAttribute("race", race);

        if (conflicts.size() > 0) {
            model.addAttribute("succeeded", successfulImports);
            model.addAttribute("conflicts", conflicts);
            return "race/import_merge";
        }

        var stageOptional = tournamentService.findStageById(form.getStageId());

        try {
            var season = seasonOptional.get();
            var newRace = raceService.importRacetimeRace(race, season, form.getName());
            stageOptional.ifPresent(stage -> tournamentService.createMatch(stage, newRace));
            return "redirect:/races";
        } catch (Exception e) {
            result.addError(new ObjectError("globalError", e.getMessage()));
            return importRacetimeRaceForm(model);
        }
    }
}
