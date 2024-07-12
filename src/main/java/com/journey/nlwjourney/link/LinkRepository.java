package com.journey.nlwjourney.link;

import com.journey.nlwjourney.trip.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LinkRepository extends JpaRepository<Link, UUID> {
    List<Link> findByTripId(UUID id);
    Optional<Link> findByUrlAndTrip(String url, Trip trip);
}
