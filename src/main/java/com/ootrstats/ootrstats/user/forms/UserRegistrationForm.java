package com.ootrstats.ootrstats.user.forms;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UserRegistrationForm {
    @NotEmpty(message = "Please enter a username.")
    @Size(min = 4, max = 32, message = "Please enter a username of at least 4 character and at most 32 characters.")
    private String username;

    @NotEmpty(message = "Please enter an email address.")
    @Email(message = "Please enter a valid email address.")
    private String emailAddress;

    @NotEmpty(message = "Please enter a password.")
    private String password;
}
