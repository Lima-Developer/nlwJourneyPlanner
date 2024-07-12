package com.journey.nlwjourney.handler;

import com.journey.nlwjourney.exception.LinkAlreadyRegisteredException;
import com.journey.nlwjourney.exception.dto.LinkAlreadyRegisteredDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class LinkAlreadyRegisteredHandler {

    @ResponseStatus(HttpStatus.ALREADY_REPORTED)
    @ResponseBody
    @ExceptionHandler(LinkAlreadyRegisteredException.class)
    public LinkAlreadyRegisteredDTO handler(LinkAlreadyRegisteredException exception){
        return new LinkAlreadyRegisteredDTO(
                exception.getMessage(),
                exception.getUrl()
        );
    }
}
