package com.musec.musec.data.models.bindingModels;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class albumBindingModel {
    @Length(min = 2, max = 20)
    @NotNull
    private String albumName;
    @NotNull
    private MultipartFile albumPic;
}
