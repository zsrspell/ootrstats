CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS users
(
    id            bigserial PRIMARY KEY,
    username      varchar(32)              NOT NULL,
    email_address varchar(128)             NOT NULL,
    password      varchar(128)             NOT NULL,
    enabled       boolean                  NOT NULL DEFAULT true,
    banned        boolean                  NOT NULL DEFAULT false,
    created_at    timestamp with time zone NOT NULL,
    modified_at   timestamp with time zone NOT NULL
);

CREATE UNIQUE INDEX IF NOT EXISTS users_username_key ON users (upper(username));
CREATE UNIQUE INDEX IF NOT EXISTS users_email_address_key ON users (upper(email_address));

CREATE TABLE IF NOT EXISTS speedrunners
(
    id             bigserial PRIMARY KEY,
    user_id        bigint REFERENCES users (id) ON DELETE SET NULL ON UPDATE CASCADE,
    name           varchar(32) NOT NULL,
    country        varchar(2),
    racetime_id    varchar(32) UNIQUE,
    racetime_name  varchar(32),
    twitch_channel varchar(32) UNIQUE
);

CREATE UNIQUE INDEX IF NOT EXISTS speedrunners_name_key ON speedrunners (upper(name));

CREATE TABLE IF NOT EXISTS teams
(
    id   bigserial PRIMARY KEY,
    name varchar(32) NOT NULL
);

CREATE TABLE IF NOT EXISTS team_members
(
    team_id        bigint PRIMARY KEY REFERENCES teams (id) ON DELETE CASCADE ON UPDATE CASCADE,
    speedrunner_id bigint REFERENCES speedrunners (id),
    UNIQUE (team_id, speedrunner_id)
);

CREATE TABLE IF NOT EXISTS games
(
    id         bigserial PRIMARY KEY,
    name       varchar(64) NOT NULL,
    short_name varchar(8)  NOT NULL,
    slug       varchar(8)  NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS rulesets
(
    id         bigserial PRIMARY KEY,
    game_id    bigint      NOT NULL REFERENCES games (id) ON DELETE CASCADE ON UPDATE CASCADE,
    name       varchar(32) NOT NULL,
    short_name varchar(8)  NOT NULL,
    slug       varchar(8)  NOT NULL,
    UNIQUE (game_id, slug)
);

CREATE TABLE IF NOT EXISTS seasons
(
    id         bigserial PRIMARY KEY,
    ruleset_id bigint NOT NULL REFERENCES rulesets (id) ON DELETE CASCADE ON UPDATE CASCADE,
    name       int    NOT NULL,
    UNIQUE (ruleset_id, name)
);

CREATE TABLE IF NOT EXISTS races
(
    id          bigserial PRIMARY KEY,
    season_id   bigint REFERENCES seasons (id) ON DELETE SET NULL ON UPDATE CASCADE,
    name        varchar(128)             NOT NULL,
    description text,
    started_at  timestamp with time zone NOT NULL
);

CREATE TABLE IF NOT EXISTS tournaments
(
    id         bigserial PRIMARY KEY,
    name       varchar(64) NOT NULL,
    short_name varchar(16) NOT NULL,
    slug       varchar(16) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS stages
(
    id            bigserial PRIMARY KEY,
    tournament_id bigint REFERENCES tournaments (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS qualifier_stages
(
    stage_id bigint PRIMARY KEY UNIQUE REFERENCES stages (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS matches
(
    id       bigserial PRIMARY KEY,
    stage_id bigint NOT NULL REFERENCES stages (id) ON DELETE CASCADE ON UPDATE CASCADE,
    race_id  bigint REFERENCES races (id) ON DELETE SET NULL ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS entrants
(
    id          bigserial PRIMARY KEY,
    race_id     bigint      NOT NULL REFERENCES races (id) ON DELETE CASCADE ON UPDATE CASCADE,
    name        varchar(32) NOT NULL,
    finish_time interval
);

CREATE TABLE IF NOT EXISTS speedrunner_entrants
(
    entrant_id     bigint PRIMARY KEY REFERENCES entrants (id) ON DELETE CASCADE ON UPDATE CASCADE,
    speedrunner_id bigint REFERENCES speedrunners (id),
    UNIQUE (entrant_id, speedrunner_id)
);

CREATE TABLE IF NOT EXISTS team_entrants
(
    entrant_id bigint PRIMARY KEY REFERENCES entrants (id) ON DELETE CASCADE ON UPDATE CASCADE,
    team_id    bigint REFERENCES teams (id),
    UNIQUE (entrant_id, team_id)
);
