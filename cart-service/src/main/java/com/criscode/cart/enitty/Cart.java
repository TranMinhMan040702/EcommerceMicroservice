package com.criscode.cart.enitty;

import com.criscode.common.entity.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Cart extends AbstractEntity {

    @Column(name = "user_id")
    private Integer userId;

    @OneToMany(mappedBy = "cart", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<CartItem> cartItems;

}
