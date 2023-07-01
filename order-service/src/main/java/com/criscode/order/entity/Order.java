package com.criscode.order.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Order extends AbstractEntity {
    @NotNull
    @Column(name = "address")
    private String address;

    @NotNull
    @Column(name = "phone")
    private String phone;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusOrder status;

    @Column(name = "is_paid_before")
    private boolean isPaidBefore = false;

    @NotNull
    @Min(0)
    @Column(name = "amount_from_user")
    private double amountFromUser;

    @Column(name = "user_id")
    private Integer userId;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;

    @ManyToOne
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;
}
