package org.defix.services.chat.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ChatDetailsApiDTO {
    private String targetUsername;
    private int targetId;
}
