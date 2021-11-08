package com.musec.musec.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.io.File;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "Users")
@Getter
@Setter
public class userEntity extends baseEntity{
    @Length(min = 2, max = 10)
    @Column(unique = true)
    private String username;
    private String password;
    @Column(unique = true)
    private String email;
    private String fullName;
    private LocalDate birthday;
    @Lob
    private File profilePic;
    @ManyToMany(mappedBy = "users")
    private Set<roleEntity> role;
    @OneToMany(mappedBy = "uploader")
    private Set<albumEntity> albums;
    @OneToMany(mappedBy = "uploader")
    private Set<singleEntity> singles;
    @OneToMany(mappedBy = "playlistCreator")
    private Set<playlistEntity> playlists;
}
