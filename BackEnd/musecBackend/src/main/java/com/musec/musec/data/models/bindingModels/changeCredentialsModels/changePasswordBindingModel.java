package com.musec.musec.data.models.bindingModels.changeCredentialsModels;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class changePasswordBindingModel extends baseChangeBindingModel{
    @NotNull
    private String newPassword;
}
