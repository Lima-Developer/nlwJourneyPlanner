package com.journey.nlwjourney.handler;

import com.journey.nlwjourney.exception.ActivityAlreadyRegisteredException;
import com.journey.nlwjourney.exception.dto.ActivityAlreadyRegisteredDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ActivityAlreadyRegisteredHandler {

    @ResponseStatus(HttpStatus.ALREADY_REPORTED)
    @ResponseBody
    @ExceptionHandler(ActivityAlreadyRegisteredException.class)
    public ActivityAlreadyRegisteredDTO handler(ActivityAlreadyRegisteredException exception){
        return new ActivityAlreadyRegisteredDTO(
                exception.getMessage(),
                exception.getTitle()
        );
    }
}
