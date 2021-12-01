package com.musec.musec.data.models.bindingModels.changeCredentialsModels;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class changeUsernameBindingModel extends baseChangeBindingModel{
    private String newUsername;
}
