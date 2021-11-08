package com.musec.musec.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.io.File;

@Entity
@Table(name = "Singles")
@Getter
@Setter
public class singleEntity extends baseEntity{
    @Length(min = 2, max = 20)
    private String singleName;
    @Lob
    private File singlePic;
    @ManyToOne
    private userEntity uploader;
    @OneToOne
    private songEntity song;
}
