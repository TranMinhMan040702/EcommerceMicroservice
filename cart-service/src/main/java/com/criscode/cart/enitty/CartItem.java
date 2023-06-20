package com.criscode.cart.enitty;

import com.criscode.common.entity.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "cart_item")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CartItem extends AbstractEntity {

    @NotNull
    @Min(1)
    @Column(name = "count")
    private int count;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @Column(name = "product_id")
    private Integer productId;
}
