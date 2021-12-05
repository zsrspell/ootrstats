package com.ootrstats.ootrstats.race.forms;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class RacetimeImportForm {
    @Size(max = 128)
    private String name;

    @NotEmpty
    private String slug;

    private long seasonId;
    private long stageId;
}
