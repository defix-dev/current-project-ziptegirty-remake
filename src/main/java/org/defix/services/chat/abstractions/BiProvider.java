package org.defix.services.chat.abstractions;

public interface BiProvider<RT, IT> {
    RT provide(IT v1, IT v2);
}
