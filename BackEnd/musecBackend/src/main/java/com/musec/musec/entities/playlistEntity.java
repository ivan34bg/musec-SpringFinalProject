package com.musec.musec.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "Playlists")
@Getter
@Setter
public class playlistEntity extends baseEntity{
    @Length(min = 2, max = 20)
    private String playlistName;
    @NotNull
    private boolean privateOrPublic;
    @ManyToOne
    private userEntity playlistCreator;
    @ManyToMany
    private Set<songEntity> songs;
}
