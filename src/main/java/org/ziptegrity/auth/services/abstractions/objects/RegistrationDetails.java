package org.ziptegrity.auth.services.abstractions.objects;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegistrationDetails {
    @NotNull
    @Size(min = 3, max = 64)
    private String username;

    @NotNull
    @Size(min = 8, max = 255)
    private String password;
}
