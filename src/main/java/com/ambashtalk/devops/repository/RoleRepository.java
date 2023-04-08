package com.ambashtalk.devops.repository;

import com.ambashtalk.devops.models.Role;
import com.ambashtalk.devops.models.enums.ERole;
import org.springframework.lang.NonNull;

import java.util.Optional;

public interface RoleRepository extends AbstractEntityRepository<Role> {
    Optional<Role> findByName(@NonNull ERole name);
}