package org.defix.auth.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.defix.auth.controllers.objects.Credentials;
import org.defix.auth.jwt.JwtGenerator;
import org.defix.auth.services.abstractions.AuthenticationService;
import org.defix.auth.services.exceptions.UsernameOrPasswordInvalidException;

@Service
public class DefaultAuthenticationService implements AuthenticationService {
    private static final Logger logger = LoggerFactory.getLogger(DefaultRegistrationService.class);
    private final AuthenticationManager authManager;

    @Autowired
    public DefaultAuthenticationService(AuthenticationManager authManager) {
        this.authManager = authManager;
    }

    @Override
    public String authenticate(Credentials credentials) {
        if(authManager.authenticate(new UsernamePasswordAuthenticationToken(
                credentials.getUsername(), credentials.getPassword()
        )) == null) {
            logger.error(STR."Failed to authenticate user: \{credentials.getUsername()}");
            throw new UsernameOrPasswordInvalidException();
        }
        return JwtGenerator.generateAccessToken(credentials.getUsername());
    }
}
