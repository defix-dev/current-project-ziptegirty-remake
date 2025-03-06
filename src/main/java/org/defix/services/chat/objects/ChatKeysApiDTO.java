package org.defix.services.chat.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatKeysApiDTO {
    private String publicKey;
    private String privateKey;
}
