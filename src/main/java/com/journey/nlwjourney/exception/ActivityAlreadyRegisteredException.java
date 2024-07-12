package com.journey.nlwjourney.exception;

import lombok.Getter;

@Getter
public class ActivityAlreadyRegisteredException extends RuntimeException{
    private final String title;

    public ActivityAlreadyRegisteredException(String message, String title) {
        super(message);
        this.title = title;
    }
}
