package com.ootrstats.ootrstats.race.forms;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class RacetimeImportForm {
    @Size(max = 128)
    private String name;

    @NotEmpty
    @Pattern(regexp = "[a-z0-9-]+/([a-z0-9]+-){2}[0-9]{4}")
    private String slug;

    @NotEmpty
    private long seasonId;

    private long stageId;
}
