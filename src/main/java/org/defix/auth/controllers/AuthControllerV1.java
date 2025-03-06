package org.defix.auth.controllers;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.defix.auth.controllers.objects.Credentials;
import org.defix.auth.services.CookieAuthenticationAdapter;
import org.defix.auth.services.abstractions.AuthenticationService;
import org.defix.auth.services.abstractions.RegistrationService;
import org.defix.auth.services.abstractions.objects.RegistrationDetails;

import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/authentication")
public class AuthControllerV1 {
    private final AuthenticationService authentication;
    private final RegistrationService registration;
    private static final Logger logger = LoggerFactory.getLogger(AuthControllerV1.class);

    @Autowired
    public AuthControllerV1(AuthenticationService authentication, RegistrationService registration) {
        this.authentication = authentication;
        this.registration = registration;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@Valid @RequestBody Credentials credentials, HttpServletResponse response) {
        String token = authentication.authenticate(credentials);
        response.addCookie(CookieAuthenticationAdapter.adaptLoginCookie(token));

        logger.debug("Success Authenticate!");
        return ResponseEntity.ok(
                Map.of("token", token)
        );
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid @RequestBody RegistrationDetails details) {
        logger.debug(details.getUsername() + details.getPassword());
        registration.register(details);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/logout")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> logout(HttpServletResponse res) {
        res.addCookie(CookieAuthenticationAdapter.adaptLogoutCookie());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/authorized")
    public ResponseEntity<Boolean> authorized(Principal principal) {
        return ResponseEntity.ok(principal != null);
    }
}
