package com.criscode.likeproduct.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "product_liked")
@IdClass(PrimaryKey.class)
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class LikeProduct extends AbstractEntity {

    @Id
    @Column(name = "user_id")
    private Integer userId;

    @Id
    @Column(name = "product_id")
    private Integer productId;

}
