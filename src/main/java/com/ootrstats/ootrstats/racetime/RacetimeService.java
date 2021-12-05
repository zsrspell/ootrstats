package com.ootrstats.ootrstats.racetime;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

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
    @Cacheable(value = "racetime_races", unless = "#result == null || #result.inProgress == true")
    public Optional<RacetimeRace> getRace(@NonNull String category, @NonNull String slug) {
        try {
            ResponseEntity<RacetimeRace> response = template.getForEntity(
                    String.format("/%s/%s/data", Objects.requireNonNull(category), Objects.requireNonNull(slug)),
                    RacetimeRace.class);

            return Optional.ofNullable(response.getBody());
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
