package com.criscode.identity.converter;

import com.criscode.clients.cart.CartClient;
import com.criscode.clients.user.dto.UserDto;
import com.criscode.clients.user.dto.UserReviewDto;
import com.criscode.exceptionutils.NotFoundException;
import com.criscode.identity.dto.RegisterRequest;
import com.criscode.identity.dto.UserPaging;
import com.criscode.identity.entity.User;
import com.criscode.identity.repository.RoleRepository;
import com.criscode.identity.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class UserConverter {

    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final CartClient cartClient;
    private final AddressConverter addressConverter;
    private final RoleConverter roleConverter;

    public User mapToEntity(RegisterRequest registerRequest) {
        return User.builder()
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .roles(registerRequest.getRoles().stream()
                        .map(roleRepository::findByRole)
                        .collect(Collectors.toList())
                )
                .build();
    }

    public User mapToEntity(UserDto userDto) throws ParseException {
        User user = userRepository.findById(userDto.getId()).orElseThrow(
                () -> new NotFoundException("User does not exist with id: " + userDto.getId())
        );
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        if (!(userDto.getBirthday()==null)) {
            Date birthday = formatter.parse(userDto.getBirthday());
            user.setBirthday(birthday);
        }
        BeanUtils.copyProperties(userDto, user, "password", "createdAt", "createdBy");
        return user;
    }

    public UserDto mapToDto(User user) {
        UserDto dto = new UserDto();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        BeanUtils.copyProperties(user, dto);
        if (!(user.getBirthday() == null)) {
            dto.setBirthday(formatter.format(user.getBirthday()));
        }
        dto.setCartId(cartClient.findCartByUser(user.getId()).getId());
        dto.setRoles(roleConverter.mapToDto(user.getRoles()));
        dto.setAddresses(addressConverter.mapToDto(user.getAddresses()));
        return dto;
    }

    public List<UserDto> mapToDto(List<User> users) {
        return users.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public UserPaging mapToDto(Page<User> users) {
        UserPaging response = new UserPaging();
        response.setPage(users.getPageable().getPageNumber());
        response.setLimit(users.getPageable().getPageSize());
        response.setTotalPage(users.getTotalPages());
        response.setUsers(users.stream().map(this::mapToDto).collect(Collectors.toList()));
        return response;
    }

}
