package com.musec.musec.entities.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class songBindingModel {
    private String songName;
    private MultipartFile songFile;
    private String genre;
}
