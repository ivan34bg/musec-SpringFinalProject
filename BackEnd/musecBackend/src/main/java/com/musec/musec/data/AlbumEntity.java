package com.musec.musec.data;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Table(name = "Albums")
@Getter
@Setter
public class AlbumEntity extends BaseEntity {
    @Length(min = 2, max = 20)
    @NotBlank
    private String albumName;
    private String albumPicLocation;
    private String albumPicFilePath;
    @OneToMany(mappedBy = "album")
    private List<SongEntity> songs;
    @ManyToOne
    private UserEntity uploader;
}
