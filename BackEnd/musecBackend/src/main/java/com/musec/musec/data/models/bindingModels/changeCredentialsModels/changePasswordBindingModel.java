package com.musec.musec.data.models.bindingModels.changeCredentialsModels;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class changePasswordBindingModel {
    private String newPassword;
    private String oldPassword;
}
