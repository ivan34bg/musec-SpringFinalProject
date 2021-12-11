package com.musec.musec.data;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Table(name = "queues")
@Entity
@Getter
@Setter
public class queueEntity extends baseEntity{
    @ManyToMany
    private List<songEntity> songs;
    @OneToOne
    private userEntity user;
}
