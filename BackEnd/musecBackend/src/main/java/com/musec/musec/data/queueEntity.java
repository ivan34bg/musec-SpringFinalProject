package com.musec.musec.data;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Table(name = "queues")
@Entity
@Getter
@Setter
public class queueEntity extends baseEntity{
    @ManyToMany
    private Set<songEntity> songs;
    @OneToOne
    private userEntity user;
}
