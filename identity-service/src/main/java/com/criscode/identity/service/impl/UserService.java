package com.criscode.identity.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.criscode.amqp.RabbitMQMessageProducer;
import com.criscode.clients.mail.OtpClient;
import com.criscode.clients.user.dto.UserDto;
import com.criscode.clients.user.dto.UserExistResponse;
import com.criscode.clients.user.dto.UserResponse;
import com.criscode.exceptionutils.NotFoundException;
import com.criscode.identity.converter.UserConverter;
import com.criscode.identity.dto.ResetPasswordRequest;
import com.criscode.identity.dto.ResetPasswordResponse;
import com.criscode.identity.dto.UserPaging;
import com.criscode.identity.entity.User;
import com.criscode.identity.repository.UserRepository;
import com.criscode.identity.service.IUserService;
import com.criscode.identity.specification.UserSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Transactional
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final Cloudinary cloudinary;
    private final PasswordEncoder passwordEncoder;
    private final RabbitMQMessageProducer producer;
    private final OtpClient resetPasswordClient;
    @Value("${rabbitmq.exchanges.topic}")
    private String exchange;
    @Value("${rabbitmq.routing-key.forgot-pass}")
    private String forgotPassRoutingKey;


    @Override
    public UserExistResponse checkUserExisted(Integer id) {
        return userRepository.findById(id).isPresent()
                ? new UserExistResponse(true)
                : new UserExistResponse(false);
    }

    @Override
    public UserPaging findAll(Integer page, Integer limit, String sortBy, String search) {

        PageRequest paging = PageRequest.of(page, limit, Sort.by(sortBy).descending());
        Specification<User> specification = UserSpecification.getSpecification(search);

        Page<User> users = userRepository.findAll(specification, paging);

        return userConverter.mapToDto(users);
    }

    @Override
    public List<UserDto> findAll() {
        return userConverter.mapToDto(userRepository.findAll());
    }

    @Override
    public UserResponse getUser(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("User does not exist with id: " + userId)
        );
        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .avatar(user.getAvatar())
                .build();
    }

    @Override
    public UserDto findOneByUserId(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("User does not exist with id: " + userId)
        );
        return userConverter.mapToDto(user);
    }

    @Override
    public UserDto updateUser(UserDto userDto, MultipartFile file) throws ParseException {
        User user = userConverter.mapToEntity(userDto);
        String avatarOld = user.getAvatar();

        if (file != null && !file.getOriginalFilename().equals(avatarOld)) {
            user.setAvatar(saveAvatarCloudinary(file));
        }
        User userResp = userRepository.save(user);
        return userConverter.mapToDto(userResp);
    }

    @Override
    public String saveAvatarCloudinary(MultipartFile file) {
        Map<?, ?> r;
        try {
            r = this.cloudinary.uploader().upload(file.getBytes(),
                    ObjectUtils.asMap("resource_type", "auto"));
            return (String) r.get("secure_url");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public ResetPasswordResponse resetPassword(ResetPasswordRequest resetPasswordRequest) {

        Optional<User> user = userRepository.findByEmail(resetPasswordRequest.getEmail());

        if (user.isPresent()) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String passwordCurrent = resetPasswordRequest.getPasswordCurrent();
            if (encoder.matches(passwordCurrent, user.get().getPassword())) {
                user.get().setPassword(passwordEncoder.encode(resetPasswordRequest.getPasswordNew()));
                userRepository.save(user.get());
                return ResetPasswordResponse.builder().status(HttpStatus.OK.value()).message("Reset Password Success").build();
            }
        }
        return ResetPasswordResponse.builder().status(HttpStatus.BAD_REQUEST.value()).message("Reset Password Fail").build();
    }

    @Override
    public void forgotPassword(String email) {
        producer.publish(email, exchange, forgotPassRoutingKey);
    }

    @Override
    public ResetPasswordResponse saveNewPassword(ResetPasswordRequest resetPasswordRequest) {

        Boolean checkCode = resetPasswordClient.checkCodeAndEmail(resetPasswordRequest.getCode(),
                resetPasswordRequest.getEmail());

        if (checkCode) {
            Optional<User> user = userRepository.findByEmail(resetPasswordRequest.getEmail());
            user.get().setPassword(passwordEncoder.encode(resetPasswordRequest.getPasswordNew()));
            userRepository.save(user.get());
            resetPasswordClient.clearCode(resetPasswordRequest.getCode(), resetPasswordRequest.getEmail());

            return ResetPasswordResponse.builder().status(HttpStatus.OK.value()).message("Change Password Success").build();
        }
        return ResetPasswordResponse.builder().status(HttpStatus.BAD_REQUEST.value()).message("Change Password Fail").build();

    }

}
