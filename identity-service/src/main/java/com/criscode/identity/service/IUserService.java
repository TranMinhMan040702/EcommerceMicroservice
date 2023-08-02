package com.criscode.identity.service;

import com.criscode.clients.user.dto.UserDto;
import com.criscode.clients.user.dto.UserExistResponse;
import com.criscode.clients.user.dto.UserResponse;
import com.criscode.identity.dto.ResetPasswordRequest;
import com.criscode.identity.dto.ResetPasswordResponse;
import com.criscode.identity.dto.UserPaging;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.util.List;

@Service
public interface IUserService {
    UserExistResponse checkUserExisted(Integer id);

    UserPaging findAll(Integer page, Integer limit, String sortBy, String search);

    List<UserDto> findAll();

    UserResponse getUser(Integer userId);

    UserDto findOneByUserId(Integer userId);

    UserDto updateUser(UserDto userDto, MultipartFile file) throws ParseException;

    String saveAvatarCloudinary(MultipartFile file);

    ResetPasswordResponse resetPassword(ResetPasswordRequest resetPasswordRequest);

    void forgotPassword(String email);

    ResetPasswordResponse saveNewPassword(ResetPasswordRequest resetPasswordRequest);
}
