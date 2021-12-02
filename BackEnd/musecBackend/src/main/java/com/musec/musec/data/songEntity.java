package com.musec.musec.data;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "Songs")
@Getter
@Setter
public class songEntity extends baseEntity{
    @Length(min = 2, max = 20)
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
    private Set<playlistEntity> playlists;
    @ManyToMany(mappedBy = "songs")
    private Set<queueEntity> queues;
}
