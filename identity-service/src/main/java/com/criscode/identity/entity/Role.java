package com.criscode.identity.entity;

import com.criscode.common.entity.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "role")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Role extends AbstractEntity {
    @Column(name = "role")
    private String role;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<User> users;
}
