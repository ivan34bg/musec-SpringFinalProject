package com.musec.musec.data;

import com.musec.musec.data.enums.roleEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "Roles")
@Getter
@Setter
public class roleEntity extends baseEntity {
    @Enumerated(value = EnumType.STRING)
    private roleEnum roleName;
    @ManyToMany(mappedBy = "roles")
    private Set<userEntity> users;
}
