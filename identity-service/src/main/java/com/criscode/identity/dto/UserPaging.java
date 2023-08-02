package com.criscode.identity.dto;

import com.criscode.clients.user.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserPaging {
    private Integer page;
    private Integer limit;
    private Integer totalPage;
    private List<UserDto> users;
}
