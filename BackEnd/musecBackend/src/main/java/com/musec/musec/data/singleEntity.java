package com.musec.musec.data;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Singles")
@Getter
@Setter
public class singleEntity extends baseEntity{
    @Length(min = 2, max = 20)
    @NotNull
    private String singleName;
    private String singlePicLocation;
    private String singlePicFilePath;
    @ManyToOne
    private userEntity uploader;
    @OneToOne(mappedBy = "single")
    private songEntity song;
}
