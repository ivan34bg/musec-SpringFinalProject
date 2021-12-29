package com.musec.musec.data.models.bindingModels.changeCredentialsModels;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class ChangeUsernameBindingModel extends BaseChangeBindingModel {
    @NotBlank
    @Length(min = 2, max = 10)
    private String newUsername;
}
