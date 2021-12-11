package com.musec.musec.data.models.bindingModels;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class singleBindingModel {
    @Length(min = 2, max = 20)
    @NotNull
    private String singleName;
    @NotNull
    private MultipartFile singlePic;
}
