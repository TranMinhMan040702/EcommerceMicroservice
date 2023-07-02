package com.criscode.likeproduct.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "product_liked")
public class LikeProduct extends AbstractEntity{

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "product_id")
    private Integer productId;

}
