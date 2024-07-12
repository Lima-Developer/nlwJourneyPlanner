package com.journey.nlwjourney.exception.dto;

import java.util.UUID;

public record ParticipantAlreadyRegisteredDTO(String message, String email, UUID trip) {
}
