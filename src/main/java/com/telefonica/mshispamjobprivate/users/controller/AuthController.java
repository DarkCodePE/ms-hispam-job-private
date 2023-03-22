package com.telefonica.mshispamjobprivate.users.controller;

import com.telefonica.mshispamjobprivate.config.CustomUserDetails;
import com.telefonica.mshispamjobprivate.event.OnUserRegistrationCompleteEvent;
import com.telefonica.mshispamjobprivate.event.payload.ApiResponse;
import com.telefonica.mshispamjobprivate.event.payload.JwtAuthenticationResponse;
import com.telefonica.mshispamjobprivate.event.payload.LoginRequest;
import com.telefonica.mshispamjobprivate.event.payload.RegistrationRequest;
import com.telefonica.mshispamjobprivate.exception.UserLoginException;
import com.telefonica.mshispamjobprivate.exception.UserRegistrationException;
import com.telefonica.mshispamjobprivate.security.JwtTokenProvider;
import com.telefonica.mshispamjobprivate.users.entity.RefreshToken;
import com.telefonica.mshispamjobprivate.users.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@Slf4j(topic = "AUTH_CONTROLLER")
public class AuthController {
    private final AuthService authService;
    private final JwtTokenProvider tokenProvider;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public AuthController(AuthService authService, JwtTokenProvider tokenProvider, ApplicationEventPublisher applicationEventPublisher) {
        this.authService = authService;
        this.tokenProvider = tokenProvider;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @PostMapping("/login")
    public ResponseEntity authenticateUser( @Valid @RequestBody LoginRequest loginRequest) {
        log.info("EMAIL ->,{}", loginRequest.getEmail());
        log.info("Password ->,{}", loginRequest.getPassword());
        Authentication authentication = authService.authenticateUser(loginRequest)
                .orElseThrow(() -> new UserLoginException("Couldn't login user [" + loginRequest + "]"));

        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        log.info("Logged in User returned [API]: " + customUserDetails.getUsername());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return authService.createAndPersistRefreshTokenForDevice(authentication, loginRequest)
                .map(RefreshToken::getToken)
                .map(refreshToken -> {
                    String jwtToken = authService.generateToken(customUserDetails);
                    return ResponseEntity.ok(new JwtAuthenticationResponse(jwtToken, refreshToken, tokenProvider.getExpiryDuration()));
                })
                .orElseThrow(() -> new UserLoginException("Couldn't create refresh token for: [" + loginRequest + "]"));
    }

    @PostMapping("/register")
    public ResponseEntity registerUser(@Valid @RequestBody RegistrationRequest registrationRequest) {

        return authService.registerUser(registrationRequest)
                .map(user -> {
                    UriComponentsBuilder urlBuilder = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api" +
                            "/auth/registrationConfirmation");
                    OnUserRegistrationCompleteEvent onUserRegistrationCompleteEvent =
                            new OnUserRegistrationCompleteEvent(user, urlBuilder);
                    applicationEventPublisher.publishEvent(onUserRegistrationCompleteEvent);
                    log.info("Registered User returned [API[: " + user);
                    return ResponseEntity.ok(new ApiResponse(true, "User registered successfully. Check your email " +
                            "for verification"));
                })
                .orElseThrow(() -> new UserRegistrationException(registrationRequest.getEmail(), "Missing user object" +
                        " in database"));
    }

}
