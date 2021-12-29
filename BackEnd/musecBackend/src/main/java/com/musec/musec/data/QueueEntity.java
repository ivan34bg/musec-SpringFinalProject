package com.musec.musec.data;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Table(name = "queues")
@Entity
@Getter
@Setter
public class QueueEntity extends BaseEntity {
    @ManyToMany
    private List<SongEntity> songs;
    @OneToOne
    private UserEntity user;
}
