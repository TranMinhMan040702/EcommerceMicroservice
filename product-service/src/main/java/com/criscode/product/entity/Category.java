package com.criscode.product.entity;

import com.criscode.common.entity.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "category")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Category extends AbstractEntity {
    @Column(name = "name", unique = true, length = 32)
    @NotNull
    private String name;

    @Column(name = "image")
    private String image;

    @Column(name = "is_deleted")
    private boolean isDeleted = false;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Product> products;
}
