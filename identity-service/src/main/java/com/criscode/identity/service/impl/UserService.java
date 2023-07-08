package com.criscode.identity.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.criscode.clients.user.dto.UserDto;
import com.criscode.clients.user.dto.UserExistResponse;
import com.criscode.clients.user.dto.UserReviewDto;
import com.criscode.exceptionutils.NotFoundException;
import com.criscode.identity.converter.UserConverter;
import com.criscode.identity.dto.UserPaging;
import com.criscode.identity.entity.User;
import com.criscode.identity.repository.UserRepository;
import com.criscode.identity.service.IUserService;
import com.criscode.identity.specification.UserSpecification;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

@Component
@AllArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final Cloudinary cloudinary;

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
    public UserReviewDto getUserReview(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("User does not exist with id: " + userId)
        );
        return UserReviewDto.builder()
                .id(user.getId())
                .firstname(user.getFirstName())
                .lastname(user.getLastName())
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

}
