package com.criscode.mail.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "reset_password")
@IdClass(PrimaryKey.class)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResetPassword {
    @Id
    @Column(name = "code")
    private String code;

    @Id
    @Column(name = "email")
    private String email;

    @Column(name = "expired")
    private Date expirationDate;
}
