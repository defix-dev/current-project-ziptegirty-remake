package org.defix.services.chat.abstractions;

public interface Provider<RT, IT> {
    RT provide(IT v);
}
