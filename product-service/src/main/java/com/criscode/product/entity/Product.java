package com.criscode.product.entity;

import com.criscode.common.entity.AbstractEntity;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@Entity
@Table(name = "product")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Product extends AbstractEntity {
    @Column(name = "name", length = 100)
    @NotNull
    private String name;

    @Column(name = "description", length = Integer.MAX_VALUE)
    @NotNull
    private String description;

    @NotNull
    @Min(0)
    @Column(name = "price")
    private Double price;

    @NotNull
    @Min(0)
    @Column(name = "promotional_price")
    private Double promotionalPrice;

    @NotNull
    @Min(0)
    @Column(name = "quantity")
    private Integer quantity;

    @Min(0)
    @Column(name = "sold")
    private Integer sold = 0;

    @Min(0)
    @Max(5)
    @Column(name = "rating")
    private Integer rating = 3;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ImageProduct> images;
}
