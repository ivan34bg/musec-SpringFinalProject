package com.musec.musec.data.models.bindingModels;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class UserRegisterBindingModel {
    @Length(min = 2, max = 10, message = "Username should be between 2 and 10 characters long")
    @NotBlank
    private String username;
    @NotBlank(message = "Password cannot be empty")
    private String password;
    @NotBlank(message = "Email cannot be empty")
    @Pattern(regexp = "[\\w]+[@][a-zA-Z0-9.]+", message = "Invalid email")
    private String email;
    @NotBlank(message = "Name cannot be empty")
    private String fullName;
    private String birthday;
}
