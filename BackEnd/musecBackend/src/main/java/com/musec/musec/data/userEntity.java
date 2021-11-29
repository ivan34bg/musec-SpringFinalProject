package com.musec.musec.data;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
    @NotNull
    private String password;
    @Column(unique = true)
    @NotNull
    private String email;
    @NotNull
    private String fullName;
    private LocalDate birthday;
    @Lob
    private String profilePicLink;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<roleEntity> roles;
    @OneToMany(mappedBy = "uploader")
    private Set<albumEntity> albums;
    @OneToMany(mappedBy = "uploader")
    private Set<singleEntity> singles;
    @OneToMany(mappedBy = "playlistCreator")
    private Set<playlistEntity> playlists;
    @OneToMany(mappedBy = "uploader")
    private Set<songEntity> songs;
    @OneToOne(mappedBy = "user")
    private queueEntity queue;
}
