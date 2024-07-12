package com.journey.nlwjourney.handler;

import com.journey.nlwjourney.exception.ParticipantAlreadyRegisteredException;
import com.journey.nlwjourney.exception.dto.ParticipantAlreadyRegisteredDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ParticipantAlreadyRegisteredHandler {

    @ResponseStatus(HttpStatus.ALREADY_REPORTED)
    @ResponseBody
    @ExceptionHandler(ParticipantAlreadyRegisteredException.class)
    public ParticipantAlreadyRegisteredDTO handler(ParticipantAlreadyRegisteredException exception){
        return new ParticipantAlreadyRegisteredDTO(
                exception.getMessage(),
                exception.getEmail(),
                exception.getTripId()
        );
    }
}
