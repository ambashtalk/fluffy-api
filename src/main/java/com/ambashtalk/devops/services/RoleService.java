package com.ambashtalk.devops.services;

import com.ambashtalk.devops.exceptions.role.RoleNotFoundException;
import com.ambashtalk.devops.models.Role;
import com.ambashtalk.devops.models.enums.ERole;
import com.ambashtalk.devops.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.Set;

@Service
public class RoleService {
    public static final String ROLE_ADMIN = "admin";
    public static final String ROLE_USER = "user";
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Set<Role> getRolesFromStringSet(Set<String> strRoles) {
        Set<Role> roles = new LinkedHashSet<>();
        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(RoleNotFoundException::new);
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case ROLE_ADMIN:
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(RoleNotFoundException::new);
                        roles.add(adminRole);
                        break;
                    case ROLE_USER:
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(RoleNotFoundException::new);
                        roles.add(userRole);
                        break;
                }
            });
        }

        return roles;
    }

}
