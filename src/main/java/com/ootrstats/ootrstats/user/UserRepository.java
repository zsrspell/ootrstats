package com.ootrstats.ootrstats.user;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    Optional<User> findUserByUsername(String username);

    boolean existsByUsernameOrEmailAddress(String username, String emailAddress);
}
