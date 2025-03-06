package org.defix.auth.services.abstractions;

import org.defix.auth.services.abstractions.objects.RegistrationDetails;

public interface RegistrationService {
    void register(RegistrationDetails details);
}
