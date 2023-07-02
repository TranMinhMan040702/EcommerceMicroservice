package com.criscode.identity.service;

import com.criscode.clients.user.dto.UserExistResponse;
import com.criscode.identity.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserExistResponse checkUserExisted(Integer id) {
        return userRepository.findById(id).isPresent()
                ? new UserExistResponse(true)
                : new UserExistResponse(false);
    }

}
