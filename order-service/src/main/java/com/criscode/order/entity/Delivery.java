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
@Table(name = "delivery")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Delivery extends AbstractEntity {

    @NotNull
    @Column(name = "name", length = 100, unique = true)
    private String name;

    @Min(0)
    @NotNull
    @Column(name = "price")
    private double price;

    @NotNull
    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "is_deleted")
    private boolean isDeleted = false;

    @OneToMany(mappedBy = "delivery", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Order> orders;

}
