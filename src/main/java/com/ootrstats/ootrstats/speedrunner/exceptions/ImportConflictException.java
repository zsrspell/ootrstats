package com.ootrstats.ootrstats.speedrunner.exceptions;

import com.ootrstats.ootrstats.speedrunner.Speedrunner;
import org.springframework.lang.NonNull;

import java.util.Objects;

public class ImportConflictException extends Exception {
    private final Speedrunner speedrunner;

    public ImportConflictException(@NonNull Speedrunner speedrunner) {
        super("Import conflicts with existing speedrunner.");
        this.speedrunner = Objects.requireNonNull(speedrunner);
    }

    @NonNull
    public Speedrunner getSpeedrunner() {
        return speedrunner;
    }

    @Override
    public String getMessage() {
        return String.format("Speedrunner with name '%s' already exists.", speedrunner.getName());
    }
}
