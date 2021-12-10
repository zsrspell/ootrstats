package com.ootrstats.ootrstats.racetime.exceptions;

public class RaceNotRecordedException extends Throwable {
    public RaceNotRecordedException(String reason) {
        super(reason);
    }
}
