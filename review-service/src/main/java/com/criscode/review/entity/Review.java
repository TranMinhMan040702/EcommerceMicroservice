package com.criscode.review.entity;

import com.criscode.common.entity.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "review")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Review extends AbstractEntity {
    @NotNull
    @Column(name = "content", length = 1000)
    private String content;

    @NotNull
    @Min(0)
    @Max(5)
    @Column(name = "rating")
    private int rating;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "order_id")
    private Integer orderId;

    @Column(name = "product_id")
    private Integer productId;
}
