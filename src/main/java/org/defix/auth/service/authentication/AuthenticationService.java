package org.defix.auth.service.authentication;

import org.defix.auth.api.dto.request.Credentials;

public interface AuthenticationService {
    String authenticate(Credentials credentials);
}
