package com.musec.musec.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.awt.*;
import java.util.Set;

@Entity
@Table(name = "Genres")
@Getter
@Setter
public class genreEntity extends baseEntity{
    private String name;
    private Color color;
    @OneToMany(mappedBy = "songGenre")
    private Set<songEntity> songs;
}
