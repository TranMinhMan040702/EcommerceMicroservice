package com.criscode.order.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "order_item")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class OrderItem extends AbstractEntity {

    @NotNull
    @Min(1)
    @Column(name = "count")
    private int count;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(name = "product_id")
    private Integer productId;

}
