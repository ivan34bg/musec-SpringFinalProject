package com.musec.musec.data.models.bindingModels.changeCredentialsModels;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class changeUsernameBindingModel extends baseChangeBindingModel{
    @NotNull
    @Length(min = 2, max = 10)
    private String newUsername;
}
