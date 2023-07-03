package com.criscode.likeproduct.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

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
