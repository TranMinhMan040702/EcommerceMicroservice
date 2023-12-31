package com.criscode.cart.enitty;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "cart")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class Cart extends AbstractEntity {

    @Column(name = "user_id")
    private Integer userId;

    @OneToMany(mappedBy = "cart", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<CartItem> cartItems;

}
