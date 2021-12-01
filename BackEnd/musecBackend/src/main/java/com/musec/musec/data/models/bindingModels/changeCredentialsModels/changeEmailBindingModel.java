package com.musec.musec.data.models.bindingModels.changeCredentialsModels;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class changeEmailBindingModel extends baseChangeBindingModel{
    private String newEmail;
    private String oldPassword;
}
