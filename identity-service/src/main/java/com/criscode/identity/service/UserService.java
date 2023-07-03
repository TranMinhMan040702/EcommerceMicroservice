package com.criscode.identity.service;

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
import com.criscode.identity.specification.UserSpecification;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final Cloudinary cloudinary;

    public UserExistResponse checkUserExisted(Integer id) {
        return userRepository.findById(id).isPresent()
                ? new UserExistResponse(true)
                : new UserExistResponse(false);
    }

    public UserPaging findAll(Integer page, Integer limit, String sortBy, String search) {

        PageRequest paging = PageRequest.of(page, limit, Sort.by(sortBy).descending());
        Specification<User> specification = UserSpecification.getSpecification(search);

        Page<User> users = userRepository.findAll(specification, paging);

        return userConverter.mapToDto(users);
    }

    public List<UserDto> findAll() {
        return userConverter.mapToDto(userRepository.findAll());
    }

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

    public UserDto findOneByUserId(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("User does not exist with id: " + userId)
        );
        return userConverter.mapToDto(user);
    }
    public UserDto updateUser(UserDto userDto, MultipartFile file) throws ParseException {
        User user = userConverter.mapToEntity(userDto);
        String avatarOld = user.getAvatar();

        if (file != null && !file.getOriginalFilename().equals(avatarOld)) {
            user.setAvatar(saveAvatarCloudinary(file));
        }
        User userResp = userRepository.save(user);
        return userConverter.mapToDto(userResp);
    }

    private String saveAvatarCloudinary(MultipartFile file) {
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
