package com.musec.musec.entities.models;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
public class userRegisterBindingModel {
    @Length(min = 2, max = 10, message = "Username should be between 2 and 10 characters long")
    private String username;
    @NotNull(message = "Password cannot be empty")
    private String password;
    @NotNull(message = "Email cannot be empty")
    private String email;
    @NotNull(message = "Name cannot be empty")
    private String fullName;
    private LocalDate birthday;
}
