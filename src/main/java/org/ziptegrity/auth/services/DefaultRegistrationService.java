package org.ziptegrity.auth.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.ziptegrity.auth.services.abstractions.RegistrationService;
import org.ziptegrity.auth.services.abstractions.objects.RegistrationDetails;
import org.ziptegrity.services.user.UserService;
import org.ziptegrity.services.user.exceptions.UserAlreadyExistException;
import org.ziptegrity.database.postgresql.entities.Role;
import org.ziptegrity.database.postgresql.entities.User;
import org.ziptegrity.database.postgresql.repositories.UserRepository;

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
        logger.debug("User registered with id: "+user.getId());
    }
}
