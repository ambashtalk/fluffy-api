package com.ambashtalk.devops.repository;

import com.ambashtalk.devops.models.Person;
import org.springframework.lang.NonNull;

public interface PersonRepository extends AbstractEntityRepository<Person> {
    Person findByUsername(@NonNull String username);

    Boolean existsByUsername(@NonNull String username);

}