package com.journey.nlwjourney.exception;

import lombok.Getter;

import java.util.UUID;

@Getter
public class ParticipantAlreadyRegisteredException extends RuntimeException{
    private final UUID tripId;
    private final String email;

    public ParticipantAlreadyRegisteredException(String message, String email, UUID tripId) {
        super(message);
        this.email = email;
        this.tripId = tripId;
    }
}
