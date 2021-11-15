package com.musec.musec.data;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;

@Entity
@Table(name = "Singles")
@Getter
@Setter
public class singleEntity extends baseEntity{
    @Length(min = 2, max = 20)
    private String singleName;
    @Lob
    private String singlePicLocation;
    @ManyToOne
    private userEntity uploader;
    @OneToOne(mappedBy = "single")
    private songEntity song;
}
