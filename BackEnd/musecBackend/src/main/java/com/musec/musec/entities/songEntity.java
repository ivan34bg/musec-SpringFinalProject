package com.musec.musec.entities;

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
    private Long plays;
    @ManyToOne
    private genreEntity songGenre;
    @ManyToOne
    private albumEntity album;
    @OneToOne(mappedBy = "song")
    private singleEntity single;
    @ManyToMany(mappedBy = "songs")
    private Set<playlistEntity> playlists;
}
