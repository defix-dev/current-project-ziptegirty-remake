package org.defix.chat.service.provider;

public interface Provider<RT, IT> {
    RT provide(IT v);
}
