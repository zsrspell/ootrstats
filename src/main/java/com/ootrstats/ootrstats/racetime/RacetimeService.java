package com.ootrstats.ootrstats.racetime;

import com.ootrstats.ootrstats.racetime.api.RacetimeRace;
import com.ootrstats.ootrstats.racetime.api.RacetimeRaceDetail;
import com.ootrstats.ootrstats.racetime.api.RacetimeRacePage;
import com.ootrstats.ootrstats.racetime.api.RacetimeUser;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class RacetimeService {
    private final RestTemplate template;

    public RacetimeService() {
        template = new RestTemplate();
        template.setUriTemplateHandler(new DefaultUriBuilderFactory("https://racetime.gg"));
    }

    @NonNull
    @Cacheable("racetime_users")
    public Optional<RacetimeUser> getUser(@NonNull String id) {
        ResponseEntity<RacetimeUser> response = template.getForEntity(
                String.format("/user/%s/data", Objects.requireNonNull(id)),
                RacetimeUser.class);

        return Optional.ofNullable(response.getBody());
    }

    @NonNull
    @Cacheable(value = "racetime_races", unless = "#result == null || #result.importable == true")
    public Optional<RacetimeRaceDetail> getRace(@NonNull String category, @NonNull String slug) {
        try {
            var response = template.getForEntity(
                    String.format("/%s/%s/data", Objects.requireNonNull(category), Objects.requireNonNull(slug)),
                    RacetimeRaceDetail.class);

            return Optional.ofNullable(response.getBody());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @NonNull
    public RacetimeRacePage getRacesByCategory(@NonNull String categorySlug, int page) throws Exception {
        var response = template.getForEntity(
                String.format("/%s/races/data?page=%d", Objects.requireNonNull(categorySlug), page),
                RacetimeRacePage.class
        );

        if (response.getStatusCode() == HttpStatus.OK && response.hasBody()) {
            var body = response.getBody();
            if (body != null) {
                return body;
            }
        }

        throw new Exception("Empty response");
    }

    public Iterable<RacetimeRace> getLatestRaces(@NonNull String categorySlug) throws Exception {
        var races = getRacesByCategory(categorySlug, 1);
        return races.getRaces();
    }
}
