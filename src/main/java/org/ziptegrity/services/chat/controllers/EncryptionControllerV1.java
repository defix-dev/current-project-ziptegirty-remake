package org.ziptegrity.services.chat.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.ziptegrity.services.chat.abstractions.Creator;
import org.ziptegrity.services.chat.objects.ChatKeysApiDTO;
import org.ziptegrity.services.chat.abstractions.Provider;
import org.ziptegrity.services.chat.objects.ChatKeysCreationDetails;
import org.ziptegrity.services.user.UserService;

import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/encryption")
public class EncryptionControllerV1 {
    private final UserService userService;
    private final Provider<String, Integer> privateKeyProvider;
    private final Provider<String, Integer> publicKeyProvider;
    private final Creator<ChatKeysCreationDetails> keysCreator;

    @Autowired
    public EncryptionControllerV1(@Qualifier("privateKeyProvider") Provider<String, Integer> privateKeyProvider,
                                  @Qualifier("publicKeyProvider") Provider<String, Integer> publicKeyProvider,
                                  @Qualifier("keysCreator") Creator<ChatKeysCreationDetails> keysCreator,
                                  UserService userService) {
        this.privateKeyProvider = privateKeyProvider;
        this.publicKeyProvider = publicKeyProvider;
        this.keysCreator = keysCreator;
        this.userService = userService;
    }

    @GetMapping("/users/public/{userId}")
    public ResponseEntity<String> getPublicKey(@PathVariable("userId") int userId) {
        return ResponseEntity.ok(publicKeyProvider.provide(userId));
    }

    @GetMapping("/users/private")
    public ResponseEntity<String> getPrivateKey(Principal principal) {
        return ResponseEntity.ok(privateKeyProvider.provide(
                userService.findByUsername(principal.getName()).getId()
        ));
    }

    @PostMapping("/users/keys")
    public ResponseEntity<Void> addKeysPair(@RequestBody ChatKeysApiDTO keys, Principal principal) {
        keysCreator.create(new ChatKeysCreationDetails(
                userService.findByUsername(principal.getName()).getId(),
                keys.getPrivateKey(),
                keys.getPublicKey()
        ));
        return ResponseEntity.noContent().build();
    }
}
