package com.criscode.mail.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "otp")
@IdClass(PrimaryKey.class)
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Otp {

    @Id
    @Column(name = "code")
    private String code;

    @Id
    @Column(name = "email")
    private String email;

    @Column(name = "expired")
    private Date expirationDate;

}
