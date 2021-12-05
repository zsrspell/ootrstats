package com.ootrstats.ootrstats.speedrunner;

import com.ootrstats.ootrstats.speedrunner.projections.SpeedrunnerInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchableSpeedrunnerRepository {
    Page<SpeedrunnerInfo> findAll(SpeedrunnerSearchCriteria criteria, Pageable pageable);
}
