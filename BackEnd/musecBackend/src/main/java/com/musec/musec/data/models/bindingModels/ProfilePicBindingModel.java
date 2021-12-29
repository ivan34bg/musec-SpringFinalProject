package com.musec.musec.data.models.bindingModels;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class ProfilePicBindingModel {
    @NotBlank
    private MultipartFile newProfilePic;
}
