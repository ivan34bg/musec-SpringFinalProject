package com.musec.musec.data;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "Albums")
@Getter
@Setter
public class albumEntity extends baseEntity{
    @Length(min = 2, max = 20)
    @NotNull
    private String albumName;
    private String albumPicLocation;
    private String albumPicFilePath;
    @OneToMany(mappedBy = "album")
    private List<songEntity> songs;
    @ManyToOne
    private userEntity uploader;
}
