package com.ootrstats.ootrstats.speedrunner;

import com.ootrstats.ootrstats.race.RaceService;
import com.ootrstats.ootrstats.race.Race_;
import com.ootrstats.ootrstats.racetime.RacetimeService;
import com.ootrstats.ootrstats.speedrunner.exceptions.ImportConflictException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/speedrunners")
public class SpeedrunnerController {
    private final SpeedrunnerService speedrunnerService;
    private final RaceService raceService;
    private final RacetimeService racetimeService;

    public SpeedrunnerController(SpeedrunnerService speedrunnerService, RaceService raceService, RacetimeService racetimeService) {
        this.speedrunnerService = speedrunnerService;
        this.raceService = raceService;
        this.racetimeService = racetimeService;
    }

    @GetMapping
    public String speedrunnerList(SpeedrunnerSearchCriteria searchParams,
                                  @PageableDefault(sort = "name", size = 50) Pageable pageable,
                                  Model model) {
        model.addAttribute("search", searchParams);
        model.addAttribute("speedrunners", speedrunnerService.findAll(searchParams, pageable));
        return "speedrunner/list";
    }

    @GetMapping("/{name}")
    public String speedrunnerDetail(@PathVariable String name, Model model) {
        var speedrunner = speedrunnerService.findByName(name).orElseThrow();
        var raceHistory = raceService.findRaceHistoryBySpeedrunner(
                speedrunner,
                PageRequest.of(0, 10, Sort.by(Race_.STARTED_AT).descending()));

        model.addAttribute("speedrunner", speedrunner);
        model.addAttribute("raceHistory", raceHistory);
        return "speedrunner/detail";
    }

    @GetMapping("/import/{id}")
    public String importForm(@PathVariable String id) throws ImportConflictException {
        var userOptional = racetimeService.getUser(id);
        if (userOptional.isPresent()) {
            var speedrunner = speedrunnerService.importRacetimeUser(userOptional.get());
            return "forward:/speedrunners/" + speedrunner.getName();
        } else {
            return "speedrunner/list";
        }
    }
}
