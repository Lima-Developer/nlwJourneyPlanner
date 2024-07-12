package com.journey.nlwjourney.exception;

import lombok.Getter;

import java.util.UUID;

@Getter
public class EventNotFoundException extends RuntimeException{
    private final UUID id;

    public EventNotFoundException(String message, UUID id) {
        super(message);
        this.id = id;
    }
}
