package org.defix.services.user.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.defix.services.user.UserService;
import org.defix.services.user.objects.UserDTO;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/users")
public class UserControllerV1 {
    private final UserService userService;

    @Autowired
    public UserControllerV1(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/metadata")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserDTO> getUserInfo(Principal principal) {
        return ResponseEntity.ok(new UserDTO(
                        principal.getName(),
                        userService.findByUsername(principal.getName()).getId()
                )
        );
    }
}
