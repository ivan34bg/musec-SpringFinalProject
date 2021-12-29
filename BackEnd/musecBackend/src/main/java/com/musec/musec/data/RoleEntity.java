package com.musec.musec.data;

import com.musec.musec.data.enums.RoleEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Roles")
@Getter
@Setter
public class RoleEntity extends BaseEntity {
    @Enumerated(value = EnumType.STRING)
    private RoleEnum roleName;
    @ManyToMany(mappedBy = "roles")
    private List<UserEntity> users;
}
