package org.defix.auth.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.defix.auth.services.abstractions.RegistrationService;
import org.defix.auth.services.abstractions.objects.RegistrationDetails;
import org.defix.services.user.UserService;
import org.defix.database.postgresql.entities.Role;
import org.defix.database.postgresql.entities.User;

import java.util.Collections;

@Service
public class DefaultRegistrationService implements RegistrationService {
    private final static Logger logger = LoggerFactory.getLogger(DefaultRegistrationService.class);
    private final UserService userService;
    private final PasswordEncoder encoder;

    @Autowired
    public DefaultRegistrationService(UserService userService, PasswordEncoder encoder) {
        this.userService = userService;
        this.encoder = encoder;
    }

    @Override
    public void register(RegistrationDetails details) {
        User user = new User();
        user.setUsername(details.getUsername());
        user.setPassword(encoder.encode(details.getPassword()));
        user.setRoles(Collections.singleton(new Role(1, "USER")));

        userService.createUser(user);
        logger.debug(STR."User registered with id: \{user.getId()}");
    }
}
