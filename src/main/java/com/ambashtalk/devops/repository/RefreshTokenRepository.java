package com.ambashtalk.devops.repository;

import com.ambashtalk.devops.models.Person;
import com.ambashtalk.devops.models.RefreshToken;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Optional;

public interface RefreshTokenRepository extends AbstractEntityRepository<RefreshToken> {
    Optional<RefreshToken> findByToken(String token);

    @Modifying
    void deleteByPerson(Person person);

    @Transactional
    @Modifying
    @Query("delete from RefreshToken r where r.expiryDate < ?1")
    void deleteAllExpiredSince(Timestamp expiryDate);

}