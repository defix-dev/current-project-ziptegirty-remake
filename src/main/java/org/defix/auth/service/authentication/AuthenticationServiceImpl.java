package org.defix.auth.service.authentication;

import org.defix.auth.service.registration.RegistrationServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.defix.auth.api.dto.request.Credentials;
import org.defix.auth.service.jwt.JwtGenerator;
import org.defix.auth.exception.UsernameOrPasswordInvalidException;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private static final Logger logger = LoggerFactory.getLogger(RegistrationServiceImpl.class);
    private final AuthenticationManager authManager;

    @Autowired
    public AuthenticationServiceImpl(AuthenticationManager authManager) {
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
