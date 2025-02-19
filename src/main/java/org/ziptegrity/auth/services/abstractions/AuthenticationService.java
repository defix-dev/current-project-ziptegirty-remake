package org.ziptegrity.auth.services.abstractions;

import org.ziptegrity.auth.controllers.objects.Credentials;

public interface AuthenticationService {
    String authenticate(Credentials credentials);
}
