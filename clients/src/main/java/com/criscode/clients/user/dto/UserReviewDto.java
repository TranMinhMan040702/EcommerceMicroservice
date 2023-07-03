package com.criscode.clients.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class UserReviewDto {

    private Integer id;
    private String firstname;
    private String lastname;
    private String avatar;
}
