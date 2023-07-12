package com.criscode.clients.user.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class UserResponse {

    private Integer id;
    private String firstName;
    private String lastName;
    private String avatar;
}
