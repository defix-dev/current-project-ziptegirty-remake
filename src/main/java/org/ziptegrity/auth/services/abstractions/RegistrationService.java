package org.ziptegrity.auth.services.abstractions;

import org.ziptegrity.auth.services.abstractions.objects.RegistrationDetails;

public interface RegistrationService {
    void register(RegistrationDetails details);
}
