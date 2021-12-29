package com.musec.musec.data;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Table(name = "Playlists")
@Getter
@Setter
public class PlaylistEntity extends BaseEntity {
    @Length(min = 2, max = 20)
    @NotBlank
    private String playlistName;
    @NotBlank
    private boolean isPublic;
    @NotBlank
    private boolean openToPublicEditsOrNot;
    @ManyToOne
    private UserEntity playlistCreator;
    @ManyToMany
    private List<SongEntity> songs;
}
