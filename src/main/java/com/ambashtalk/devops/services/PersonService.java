package com.ambashtalk.devops.services;

import com.ambashtalk.devops.models.*;
import com.ambashtalk.devops.payload.response.SignInResponse;
import com.ambashtalk.devops.repository.PersonEmailRepository;
import com.ambashtalk.devops.repository.PersonRepository;
import com.ambashtalk.devops.security.jwt.JwtTokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class PersonService {
    private final PersonRepository personRepository;
    private final PersonEmailRepository personEmailRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    private final RefreshTokenService refreshTokenService;

    public PersonService(PersonRepository personRepository, PersonEmailRepository personEmailRepository,
                         AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider,
                         PasswordEncoder passwordEncoder, RefreshTokenService refreshTokenService) {
        this.personRepository = personRepository;
        this.personEmailRepository = personEmailRepository;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
        this.refreshTokenService = refreshTokenService;
    }

    public Boolean personExistsByUsername(String username) {
        return personRepository.existsByUsername(username);
    }

    public Boolean personExistsByEmail(String email) {
        return personEmailRepository.existsByEmailIgnoreCase(email);
    }

    public void signUpPerson(String username, String email, String password, Set<Role> roles) {
        var personDefaultEmail = PersonEmail.builder()
                .email(email)
                .build();
        var person = Person.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .defaultPersonEmail(personDefaultEmail)
                .roles(roles)
                .build();
        personDefaultEmail.setPerson(person);

        personRepository.save(person);
    }

    public SignInResponse signInPerson(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtTokenProvider.generateJwtToken(authentication);
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        List<String> roles = personDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(personDetails.getId());
        return SignInResponse.builder()
                .id(personDetails.getId())
                .accessToken(jwt)
                .refreshToken(refreshToken.getToken())
                .tokenType("Bearer")
                .username(personDetails.getUsername())
                .email(personDetails.getEmail())
                .roles(roles)
                .build();
    }

    public String refreshToken(Person person) {
        Authentication authentication = jwtTokenProvider.getAuthentication(person.getUsername());
        return jwtTokenProvider.generateJwtToken(authentication);
    }
}
