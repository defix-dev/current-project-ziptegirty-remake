package org.ziptegrity.services.chat.abstractions;

public interface Provider<RT, IT> {
    RT provide(IT v);
}
