package com.musec.musec.data;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Table(name = "Songs")
@Getter
@Setter
public class SongEntity extends BaseEntity {
    @Length(min = 2, max = 20)
    @NotBlank
    private String songName;
    private String songLocation;
    private String songFilePath;
    private Long plays;
    @ManyToOne
    private UserEntity uploader;
    @ManyToOne
    private GenreEntity songGenre;
    @ManyToOne
    private AlbumEntity album;
    @OneToOne
    private SingleEntity single;
    @ManyToMany(mappedBy = "songs")
    private List<PlaylistEntity> playlists;
    @ManyToMany(mappedBy = "songs")
    private List<QueueEntity> queues;
}
