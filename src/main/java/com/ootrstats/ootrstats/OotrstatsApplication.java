package com.ootrstats.ootrstats;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class OotrstatsApplication {

    public static void main(String[] args) {
        SpringApplication.run(OotrstatsApplication.class, args);
    }

}
