package org.defix.services.chat.abstractions;

public interface ReturnedCreator<RT, IT> {
    RT create(IT v);
}
