package com.ootrstats.ootrstats;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;
import java.util.TimeZone;

@Configuration
public class WebMvcConfig {
    @Bean
    SessionLocaleResolver sessionLocaleResolver() {
        var sessionLocaleResolver = new SessionLocaleResolver();
        sessionLocaleResolver.setDefaultLocale(Locale.US);
        sessionLocaleResolver.setDefaultTimeZone(TimeZone.getTimeZone("utc"));
        return sessionLocaleResolver;
    }
}
