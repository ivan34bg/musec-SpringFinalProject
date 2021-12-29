package com.musec.musec.data;

import com.musec.musec.data.enums.GenreEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Genres")
@Getter
@Setter
public class GenreEntity extends BaseEntity {
    @Enumerated(value = EnumType.STRING)
    private GenreEnum genreName;
    private String properName;
    @OneToMany(mappedBy = "songGenre")
    private List<SongEntity> songs;
}
