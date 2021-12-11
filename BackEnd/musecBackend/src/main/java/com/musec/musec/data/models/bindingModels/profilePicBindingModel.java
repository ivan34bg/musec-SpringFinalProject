package com.musec.musec.data.models.bindingModels;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class profilePicBindingModel {
    @NotNull
    private MultipartFile newProfilePic;
}
