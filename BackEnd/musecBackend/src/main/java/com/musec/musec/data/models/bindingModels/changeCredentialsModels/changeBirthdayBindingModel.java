package com.musec.musec.data.models.bindingModels.changeCredentialsModels;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class changeBirthdayBindingModel extends baseChangeBindingModel{
    private String newBirthday;
    private String oldPassword;
}
