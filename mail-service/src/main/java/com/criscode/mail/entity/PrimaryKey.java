package com.criscode.mail.entity;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class PrimaryKey implements Serializable {

    private String code;

    private String email;
}
