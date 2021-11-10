package com.musec.musec.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Albums")
@Getter
@Setter
public class albumEntity extends baseEntity{
    @Length(min = 2, max = 20)
    private String albumName;
    @Lob
    private String albumPicLocation;
    @OneToMany(mappedBy = "album")
    private Set<songEntity> songs = new HashSet<>();
    @ManyToOne
    private userEntity uploader;
}
