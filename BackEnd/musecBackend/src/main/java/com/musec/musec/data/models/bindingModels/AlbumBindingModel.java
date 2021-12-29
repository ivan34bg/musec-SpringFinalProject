package com.musec.musec.data.models.bindingModels;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class AlbumBindingModel {
    @Length(min = 2, max = 20)
    @NotBlank
    private String albumName;
    @NotBlank
    private MultipartFile albumPic;
}
