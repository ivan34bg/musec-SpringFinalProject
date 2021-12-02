package com.musec.musec.data.models.bindingModels;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class profilePicBindingModel {
    private MultipartFile newProfilePic;
}
