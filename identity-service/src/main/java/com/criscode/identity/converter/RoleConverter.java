package com.criscode.identity.converter;

import com.criscode.identity.entity.Role;
import com.criscode.identity.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class RoleConverter {

    private final RoleRepository roleRepository;

    public List<Role> mapToEntity(Set<String> roleDtos) {
        List<Role> roles = new ArrayList<>();
        for (String role : roleDtos) {
            roles.add(roleRepository.findByRole(role));
        }
        return roles;
    }

    public Set<String> mapToDto(List<Role> roleEntities) {
        Set<String> roles = new HashSet<>();
        for (Role role : roleEntities) {
            roles.add(role.getRole());
        }
        return roles;
    }
}
