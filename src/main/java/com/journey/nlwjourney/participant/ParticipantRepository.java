package com.journey.nlwjourney.participant;

import com.journey.nlwjourney.trip.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ParticipantRepository extends JpaRepository<Participant, UUID> {
    List<Participant> findByTripId(UUID tripId);
    Optional<Participant> findByEmailAndTrip(String email, Trip trip);
}
