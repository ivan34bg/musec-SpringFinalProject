package com.musec.musec.data;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "musec_users")
@Getter
@Setter
public class UserEntity extends BaseEntity {
    @Length(min = 2, max = 10)
    @Column(unique = true)
    private String username;
    @NotBlank
    private String password;
    @Column(unique = true)
    @Pattern(regexp = "[\\w]+[@][a-zA-Z0-9.]+", message = "Invalid email")
    @NotBlank
    private String email;
    @NotBlank
    private String fullName;
    private LocalDate birthday;
    private String profilePicLink;
    private String profilePicFilePath;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<RoleEntity> roles;
    @OneToMany(mappedBy = "uploader")
    private List<AlbumEntity> albums;
    @OneToMany(mappedBy = "uploader")
    private List<SingleEntity> singles;
    @OneToMany(mappedBy = "playlistCreator")
    private List<PlaylistEntity> playlists;
    @OneToMany(mappedBy = "uploader")
    private List<SongEntity> songs;
    @OneToOne(mappedBy = "user")
    private QueueEntity queue;
}
