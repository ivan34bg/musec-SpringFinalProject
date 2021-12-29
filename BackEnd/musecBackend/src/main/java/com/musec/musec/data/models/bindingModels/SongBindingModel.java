package com.musec.musec.data.models.bindingModels;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class SongBindingModel {
    @Length(min = 2, max = 20)
    @NotBlank
    private String songName;
    @NotBlank
    private MultipartFile songFile;
    @NotBlank
    private String genre;
}
