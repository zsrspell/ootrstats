package com.ootrstats.ootrstats.tournament;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TournamentRepository extends PagingAndSortingRepository<Tournament, Long> {
}
