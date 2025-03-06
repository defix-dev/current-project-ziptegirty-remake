package org.defix.auth.services.abstractions;

import org.defix.auth.controllers.objects.Credentials;

public interface AuthenticationService {
    String authenticate(Credentials credentials);
}
