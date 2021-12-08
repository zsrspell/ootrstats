package com.ootrstats.ootrstats.user;

import com.ootrstats.ootrstats.user.exceptions.UserExistsException;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository users;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository users, PasswordEncoder passwordEncoder) {
        this.users = users;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<User> findByUsername(@NonNull String username) {
        return users.findUserByUsername(Objects.requireNonNull(username));
    }

    public User createUser(@NonNull String username, @NonNull String emailAddress, @NonNull String password) throws UserExistsException {
        if (users.existsByUsernameOrEmailAddress(
                Objects.requireNonNull(username),
                Objects.requireNonNull(emailAddress))) {
            throw new UserExistsException("This username and/or email address are already in use.");
        }

        password = passwordEncoder.encode(Objects.requireNonNull(password));
        var user = new User(username, emailAddress, password);
        user.setEnabled(true);
        return users.save(user);
    }
}
