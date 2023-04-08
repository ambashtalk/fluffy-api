package com.ambashtalk.devops.controllers;

import com.ambashtalk.devops.exceptions.jwt.TokenRefreshException;
import com.ambashtalk.devops.exceptions.person.PersonAlreadyExistsException;
import com.ambashtalk.devops.models.Person;
import com.ambashtalk.devops.models.PersonDetails;
import com.ambashtalk.devops.models.RefreshToken;
import com.ambashtalk.devops.payload.request.LoginRequest;
import com.ambashtalk.devops.payload.request.SignupRequest;
import com.ambashtalk.devops.payload.request.TokenRefreshRequest;
import com.ambashtalk.devops.payload.response.BaseResponse;
import com.ambashtalk.devops.payload.response.MessageResponse;
import com.ambashtalk.devops.payload.response.SignInResponse;
import com.ambashtalk.devops.payload.response.TokenRefreshResponse;
import com.ambashtalk.devops.services.PersonService;
import com.ambashtalk.devops.services.RefreshTokenService;
import com.ambashtalk.devops.services.RoleService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController()
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final PersonService personService;
    private final RoleService roleService;
    private final RefreshTokenService refreshTokenService;

    public AuthController(PersonService personService, RoleService roleService,
                            RefreshTokenService refreshTokenService) {
        this.personService = personService;
        this.roleService = roleService;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("/signin")
    public BaseResponse<SignInResponse> signInUser(@Valid @RequestBody LoginRequest loginRequest) {
        return BaseResponse.build(personService.signInPerson(loginRequest.getUsername(), loginRequest.getPassword()));
    }

    @PostMapping("/signup")
    public BaseResponse<MessageResponse> signUpUser(@Valid @RequestBody SignupRequest signupRequest) {
        if (personService.personExistsByUsername(signupRequest.getUsername())) {
            throw new PersonAlreadyExistsException("Error: Username is already taken!");
        }
        if (personService.personExistsByEmail(signupRequest.getEmail())) {
            throw new PersonAlreadyExistsException("Error: Email is already in use!");
        }
        personService.signUpPerson(signupRequest.getUsername(), signupRequest.getEmail(), signupRequest.getPassword(),
                roleService.getRolesFromStringSet(signupRequest.getRole()));

        return BaseResponse.build(MessageResponse.builder()
                .message("User registered successfully!")
                .build());
    }

    @PostMapping("/refresh-token")
    public BaseResponse<TokenRefreshResponse> refreshToken(@Valid @RequestBody TokenRefreshRequest request) {
        String refreshToken = request.getRefreshToken();
        Person person = refreshTokenService.findByToken(refreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getPerson)
                .orElseThrow(() -> new TokenRefreshException("Refresh token is not in database"));
        String newAccessToken = personService.refreshToken(person);

        return BaseResponse.build(TokenRefreshResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .build());
    }

    @PostMapping("/signout")
    public BaseResponse<MessageResponse> logoutUser() {
        PersonDetails userDetails = (PersonDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long personId = userDetails.getId();
        refreshTokenService.deleteByPersonId(personId);
        return BaseResponse.build(MessageResponse.builder()
                .message("Log out successful!")
                .build());
    }

}
