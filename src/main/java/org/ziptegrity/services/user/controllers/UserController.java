package org.ziptegrity.services.user.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ziptegrity.services.user.UserService;
import org.ziptegrity.services.user.objects.UserDTO;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<UserDTO> getUserInfo(Principal principal) {
        return ResponseEntity.ok(new UserDTO(
                        principal.getName(),
                        userService.findByUsername(principal.getName()).getId()
                )
        );
    }
}
