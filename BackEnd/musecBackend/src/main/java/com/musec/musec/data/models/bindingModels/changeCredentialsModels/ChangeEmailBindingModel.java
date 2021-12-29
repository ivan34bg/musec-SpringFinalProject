package com.musec.musec.data.models.bindingModels.changeCredentialsModels;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class ChangeEmailBindingModel extends BaseChangeBindingModel {
    @NotBlank
    @Pattern(regexp = "[\\w]+[@][a-zA-Z0-9.]+", message = "Invalid email")
    private String newEmail;
}
