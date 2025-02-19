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
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/is_authorized")
    public ResponseEntity<Boolean> authorized(Principal principal) {
        return ResponseEntity.ok(principal != null);
    }

    @GetMapping("/get_user_info")
    public ResponseEntity<UserDTO> getUserInfo(Principal principal) {
        return ResponseEntity.ok(new UserDTO(
                        principal.getName(),
                        userService.findByUsername(principal.getName()).getId()
                )
        );
    }
}
