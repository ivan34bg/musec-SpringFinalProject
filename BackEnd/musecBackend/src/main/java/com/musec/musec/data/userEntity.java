package com.musec.musec.data;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "musec_users")
@Getter
@Setter
public class userEntity extends baseEntity{
    @Length(min = 2, max = 10)
    @Column(unique = true)
    private String username;
    @NotNull
    private String password;
    @Column(unique = true)
    @Pattern(regexp = "[\\w]+[@][a-zA-Z0-9.]+", message = "Invalid email")
    @NotNull
    private String email;
    @NotNull
    private String fullName;
    private LocalDate birthday;
    private String profilePicLink;
    private String profilePicFilePath;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<roleEntity> roles;
    @OneToMany(mappedBy = "uploader")
    private List<albumEntity> albums;
    @OneToMany(mappedBy = "uploader")
    private List<singleEntity> singles;
    @OneToMany(mappedBy = "playlistCreator")
    private List<playlistEntity> playlists;
    @OneToMany(mappedBy = "uploader")
    private List<songEntity> songs;
    @OneToOne(mappedBy = "user")
    private queueEntity queue;
}
