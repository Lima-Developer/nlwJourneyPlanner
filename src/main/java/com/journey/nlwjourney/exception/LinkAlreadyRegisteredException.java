package com.journey.nlwjourney.exception;

import lombok.Getter;

@Getter
public class LinkAlreadyRegisteredException extends RuntimeException{
    private final String url;

    public LinkAlreadyRegisteredException(String message, String url) {
        super(message);
        this.url = url;
    }
}
