package com.ootrstats.ootrstats.user;

import com.ootrstats.ootrstats.user.exceptions.UserExistsException;
import com.ootrstats.ootrstats.user.forms.UserRegistrationForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginForm() {
        return "users/login_form";
    }

    @GetMapping("/register")
    public String registrationForm(Model model) {
        model.addAttribute("form", new UserRegistrationForm());
        return "users/registration_form";
    }

    @PostMapping("/register")
    public String processRegistrationForm(@ModelAttribute("form") @Valid UserRegistrationForm form,
                                          BindingResult result,
                                          Model model) {
        if (result.hasErrors()) {
            return "users/registration_form";
        }

        try {
            userService.createUser(form.getUsername(), form.getEmailAddress(), form.getPassword());
        } catch (UserExistsException e) {
            result.addError(new ObjectError("globalError", e.getMessage()));
            return "users/registration_form";
        }

        return "users/registration_complete";
    }
}
