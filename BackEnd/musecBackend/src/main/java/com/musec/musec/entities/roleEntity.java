package com.musec.musec.entities;

import com.musec.musec.entities.enums.roleEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "Roles")
@Getter
@Setter
public class roleEntity extends baseEntity {
    private roleEnum name;
    @ManyToMany
    private Set<userEntity> users;
}
