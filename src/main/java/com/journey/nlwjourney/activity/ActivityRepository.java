package com.journey.nlwjourney.activity;

import com.journey.nlwjourney.participant.Participant;
import com.journey.nlwjourney.trip.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ActivityRepository extends JpaRepository<Activity, UUID> {
    List<Activity> findByTripId(UUID tripId);
    Optional<Activity> findByTitleAndTrip(String title,Trip trip);
}
