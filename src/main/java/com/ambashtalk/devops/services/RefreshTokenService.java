package com.ambashtalk.devops.services;

import com.ambashtalk.devops.exceptions.jwt.TokenRefreshException;
import com.ambashtalk.devops.exceptions.person.PersonNotFoundException;
import com.ambashtalk.devops.models.RefreshToken;
import com.ambashtalk.devops.repository.PersonRepository;
import com.ambashtalk.devops.repository.RefreshTokenRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {
    private final long jwtRefreshExpirationMs;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PersonRepository personRepository;

    public RefreshTokenService(@Value("${app.jwt.refresh-expiration-ms}") Long jwtRefreshExpirationMs,
                               RefreshTokenRepository refreshTokenRepository,
                               PersonRepository personRepository) {
        this.jwtRefreshExpirationMs = jwtRefreshExpirationMs;
        this.refreshTokenRepository = refreshTokenRepository;
        this.personRepository = personRepository;
    }

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken createRefreshToken(Long personId) {
        var refreshToken = RefreshToken.builder()
                .person(personRepository.findById(personId).orElseThrow(PersonNotFoundException::new))
                .expiryDate(Timestamp.from(Instant.now().plusMillis(jwtRefreshExpirationMs)))
                .token(UUID.randomUUID().toString())
                .build();

        return refreshTokenRepository.save(refreshToken);
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().toInstant().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new TokenRefreshException("Refresh token was expired. Please make a new signin request");
        }

        return token;
    }

    @Transactional
    public void deleteByPersonId(Long personId) {
        refreshTokenRepository.deleteByPerson(
                personRepository.findById(personId).orElseThrow(PersonNotFoundException::new)
        );
    }
}
