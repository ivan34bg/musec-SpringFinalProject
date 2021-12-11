package com.musec.musec.data;

import com.musec.musec.data.enums.genreEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.awt.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Genres")
@Getter
@Setter
public class genreEntity extends baseEntity{
    @Enumerated(value = EnumType.STRING)
    private genreEnum genreName;
    private String properName;
    @OneToMany(mappedBy = "songGenre")
    private List<songEntity> songs;
}
