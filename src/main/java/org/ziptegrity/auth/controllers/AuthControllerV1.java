package org.ziptegrity.auth.controllers;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.ziptegrity.auth.controllers.objects.Credentials;
import org.ziptegrity.auth.services.CookieAuthenticationAdapter;
import org.ziptegrity.auth.services.abstractions.AuthenticationService;
import org.ziptegrity.auth.services.abstractions.RegistrationService;
import org.ziptegrity.auth.services.abstractions.objects.RegistrationDetails;

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
        return ResponseEntity
                .ok(
                        Map.of("token", token)
                );
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid @RequestBody RegistrationDetails details) {
        logger.debug(details.getUsername() + details.getPassword());
        registration.register(details);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse res) {
        res.addCookie(CookieAuthenticationAdapter.adaptLogoutCookie());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/authorized")
    public ResponseEntity<Boolean> authorized(Principal principal) {
        return ResponseEntity.ok(principal != null);
    }
}
