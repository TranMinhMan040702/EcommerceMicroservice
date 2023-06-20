package com.criscode.address.entity;

import com.criscode.common.entity.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "address")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Address extends AbstractEntity {
    @Column(name = "username")
    private String username;

    @Column(name = "phone")
    private String phone;

    @Column(name = "ward")
    private String ward;

    @Column(name = "district")
    private String district;

    @Column(name = "province")
    private String province;

    @Column(name = "street")
    private String street;

    @Column(name = "status")
    private boolean status;

    @Column(name = "user_id")
    private Integer userId;
}
