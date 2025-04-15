package org.defix.auth.service.registration;

import org.defix.auth.service.dto.RegistrationDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.defix.user.service.UserService;
import org.defix.database.postgresql.entity.Role;
import org.defix.database.postgresql.entity.User;

import java.util.Collections;

@Service
public class RegistrationServiceImpl implements RegistrationService {
    private final static Logger logger = LoggerFactory.getLogger(RegistrationServiceImpl.class);
    private final UserService userService;
    private final PasswordEncoder encoder;

    @Autowired
    public RegistrationServiceImpl(UserService userService, PasswordEncoder encoder) {
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
