package com.musec.musec.data.models.bindingModels.changeCredentialsModels;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class BaseChangeBindingModel {
    @NotBlank
    private String oldPassword;
}
