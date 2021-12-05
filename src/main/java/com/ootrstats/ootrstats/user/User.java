package com.ootrstats.ootrstats.user;

import com.ootrstats.ootrstats.speedrunner.Speedrunner;
import org.hibernate.annotations.NaturalId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.Optional;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @NaturalId
    @Column(name = "username", nullable = false, unique = true, length = 32)
    private String username;

    @Column(name = "email_address", nullable = false, unique = true, length = 128)
    private String emailAddress;

    @Column(name = "password", nullable = false, length = 128)
    private String password;

    @Column(name = "enabled", nullable = false)
    private boolean enabled;

    @Column(name = "banned", nullable = false)
    private boolean banned;

    @OneToOne(mappedBy = "user")
    private Speedrunner speedrunner;

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt = OffsetDateTime.now();

    @LastModifiedDate
    @Column(name = "modified_at", nullable = false)
    private OffsetDateTime modifiedAt = OffsetDateTime.now();

    public User() {
    }

    public User(@NonNull String username, @NonNull String emailAddress, @NonNull String password) {
        this.username = Objects.requireNonNull(username);
        this.emailAddress = Objects.requireNonNull(emailAddress);
        this.password = Objects.requireNonNull(password);
    }

    @NonNull
    public Long getId() {
        return id;
    }

    @NonNull
    public String getUsername() {
        return username;
    }

    public void setUsername(@NonNull String username) {
        this.username = Objects.requireNonNull(username);
    }

    @NonNull
    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(@NonNull String emailAddress) {
        this.emailAddress = Objects.requireNonNull(emailAddress);
    }

    @NonNull
    public String getPassword() {
        return password;
    }

    public void setPassword(@NonNull String password) {
        this.password = Objects.requireNonNull(password);
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isBanned() {
        return banned;
    }

    public void setBanned(boolean banned) {
        this.banned = banned;
    }

    @NonNull
    public Optional<Speedrunner> getSpeedrunner() {
        return Optional.ofNullable(speedrunner);
    }

    public void setSpeedrunner(Speedrunner speedrunner) {
        this.speedrunner = speedrunner;
    }

    @NonNull
    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(@NonNull OffsetDateTime createdAt) {
        this.createdAt = Objects.requireNonNull(createdAt);
    }

    @NonNull
    public OffsetDateTime getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(@NonNull OffsetDateTime modifiedAt) {
        this.modifiedAt = Objects.requireNonNull(modifiedAt);
    }
}
