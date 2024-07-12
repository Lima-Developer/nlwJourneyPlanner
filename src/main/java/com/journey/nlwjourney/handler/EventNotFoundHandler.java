package com.journey.nlwjourney.handler;

import com.journey.nlwjourney.exception.EventNotFoundException;
import com.journey.nlwjourney.exception.dto.EventNotFoundDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class EventNotFoundHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    @ExceptionHandler(EventNotFoundException.class)
    public EventNotFoundDTO handler(EventNotFoundException exception){
        return new EventNotFoundDTO(
                exception.getMessage(),
                exception.getId()
        );
    }
}
