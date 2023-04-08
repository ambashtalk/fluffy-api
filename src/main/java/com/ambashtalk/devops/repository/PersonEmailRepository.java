package com.ambashtalk.devops.repository;

import com.ambashtalk.devops.models.Person;
import com.ambashtalk.devops.models.PersonEmail;
import org.springframework.lang.NonNull;

public interface PersonEmailRepository extends AbstractEntityRepository<PersonEmail> {
    Person findByEmailIgnoreCase(@NonNull String email);

    Boolean existsByEmailIgnoreCase(@NonNull String email);
}