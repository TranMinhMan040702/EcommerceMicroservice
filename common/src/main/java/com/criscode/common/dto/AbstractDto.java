package com.criscode.common.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public abstract class AbstractDto {
    private Integer id;
    private String createdBy;
    private Date createdAt;
    private String updatedBy;
    private Date updatedAt;
}
