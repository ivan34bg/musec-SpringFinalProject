package com.musec.musec.data;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "Songs")
@Getter
@Setter
public class songEntity extends baseEntity{
    @Length(min = 2, max = 20)
    @NotNull
    private String songName;
    private String songLocation;
    private String songFilePath;
    private Long plays;
    @ManyToOne
    private userEntity uploader;
    @ManyToOne
    private genreEntity songGenre;
    @ManyToOne
    private albumEntity album;
    @OneToOne
    private singleEntity single;
    @ManyToMany(mappedBy = "songs")
    private List<playlistEntity> playlists;
    @ManyToMany(mappedBy = "songs")
    private List<queueEntity> queues;
}
