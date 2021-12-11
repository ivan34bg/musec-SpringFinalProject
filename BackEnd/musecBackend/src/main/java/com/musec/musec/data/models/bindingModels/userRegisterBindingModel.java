package com.musec.musec.data.models.bindingModels;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Getter
@Setter
public class userRegisterBindingModel {
    @Length(min = 2, max = 10, message = "Username should be between 2 and 10 characters long")
    @NotNull
    private String username;
    @NotNull(message = "Password cannot be empty")
    private String password;
    @NotNull(message = "Email cannot be empty")
    @Pattern(regexp = "[\\w]+[@][a-zA-Z0-9.]+", message = "Invalid email")
    private String email;
    @NotNull(message = "Name cannot be empty")
    private String fullName;
    private String birthday;
}
