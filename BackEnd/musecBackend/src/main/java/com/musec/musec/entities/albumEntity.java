package com.musec.musec.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.io.File;
import java.util.Set;

@Entity
@Table(name = "Albums")
@Getter
@Setter
public class albumEntity extends baseEntity{
    @Length(min = 2, max = 20)
    private String albumName;
    @Lob
    private File albumPic;
    @OneToMany(mappedBy = "album")
    private Set<songEntity> songs;
    @ManyToOne
    private userEntity uploader;
}
