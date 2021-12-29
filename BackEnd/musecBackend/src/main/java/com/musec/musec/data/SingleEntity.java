package com.musec.musec.data;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "Singles")
@Getter
@Setter
public class SingleEntity extends BaseEntity {
    @Length(min = 2, max = 20)
    @NotBlank
    private String singleName;
    private String singlePicLocation;
    private String singlePicFilePath;
    @ManyToOne
    private UserEntity uploader;
    @OneToOne(mappedBy = "single")
    private SongEntity song;
}
