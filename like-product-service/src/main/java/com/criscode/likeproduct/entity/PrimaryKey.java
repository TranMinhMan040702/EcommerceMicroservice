package com.criscode.likeproduct.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class PrimaryKey implements Serializable {

    private Integer userId;

    private Integer productId;
}
