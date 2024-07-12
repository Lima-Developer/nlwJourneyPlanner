package com.journey.nlwjourney.trip;

import com.journey.nlwjourney.activity.*;
import com.journey.nlwjourney.exception.EventNotFoundException;
import com.journey.nlwjourney.link.LinkDTO;
import com.journey.nlwjourney.link.LinkData;
import com.journey.nlwjourney.link.LinkResponse;
import com.journey.nlwjourney.link.LinkService;
import com.journey.nlwjourney.participant.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/trips")
public class TripController {

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private ParticipantService participantService;

    @Autowired
    ActivityService activityService;

    @Autowired
    LinkService linkService;


    // TRIPS
    @PostMapping
    public ResponseEntity<TripCreateResponse> createTrip(@RequestBody TripDTO payLoad) {
        Trip newTrip = new Trip(payLoad);
        this.tripRepository.save(newTrip);
        this.participantService.registerParticipantToEvent(payLoad.emails_to_invite(), newTrip);
        return ResponseEntity.ok(new TripCreateResponse(newTrip.getId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Trip> getTripDetails(@PathVariable UUID id) {
        Optional<Trip> trip = this.tripRepository.findById(id);

        return trip.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Trip> updateTrip(@PathVariable UUID id, @RequestBody TripDTO payLoad) {
        Optional<Trip> trip = this.tripRepository.findById(id);

        if (trip.isPresent()) {
            Trip newTrip = trip.get();
            newTrip.setEndsAt(LocalDateTime.parse(payLoad.ends_at(), DateTimeFormatter.ISO_DATE_TIME));
            newTrip.setStartsAt(LocalDateTime.parse(payLoad.starts_at(), DateTimeFormatter.ISO_DATE_TIME));
            newTrip.setDestination(payLoad.destination());

            this.tripRepository.save(newTrip);

            return ResponseEntity.ok(newTrip);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/confirm")
    public ResponseEntity<Trip> confirmTrip(@PathVariable UUID id) {
        Optional<Trip> trip = this.tripRepository.findById(id);

        if (trip.isPresent()) {
            Trip newTrip = trip.get();
            newTrip.setIsConfirmed(true);
            this.tripRepository.save(newTrip);
            this.participantService.triggerConfirmationEmailToParticipants(id);

            return ResponseEntity.ok(newTrip);
        }
        return ResponseEntity.notFound().build();
    }

    // ACTIVITIES

    @PostMapping("/{id}/activities")
    public ResponseEntity<ActivityResponse> registerActivity(@PathVariable UUID id, @RequestBody ActivityDTO payLoad) {
        Optional<Trip> trip = this.tripRepository.findById(id);

        if (trip.isPresent()) {
            Trip newTrip = trip.get();

            ActivityResponse activityResponse = this.activityService.registerActivity(payLoad, newTrip);

            return ResponseEntity.ok(activityResponse);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/activities")
    public ResponseEntity<List<ActivityData>> getAllActivities(@PathVariable UUID id) throws EventNotFoundException{
        List<ActivityData> activityDataList = this.activityService.getAllActivitiesFromId(id);

        return ResponseEntity.ok(activityDataList);
    }

    // PARTICIPANT

    @PostMapping("/{id}/invite")
    public ResponseEntity<ParticipantCreateResponse> inviteParticipant(@PathVariable UUID id, @RequestBody ParticipantDTO payLoad) {
        Optional<Trip> trip = this.tripRepository.findById(id);

        if (trip.isPresent()) {
            Trip newTrip = trip.get();

            ParticipantCreateResponse participantCreateResponse = this.participantService.registerParticipantToEvent(payLoad.email(), newTrip);

            if (newTrip.getIsConfirmed())
                this.participantService.triggerConfirmationEmailToParticipant(payLoad.email());

            return ResponseEntity.ok(participantCreateResponse);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/participants")
    public ResponseEntity<List<ParticipantData>> getAllParticipants(@PathVariable UUID id) throws EventNotFoundException {
        List<ParticipantData> participantList = this.participantService.getAllParticipantsFromEvent(id);

        return ResponseEntity.ok(participantList);
    }

    // LINKS

    @PostMapping("/{id}/links")
    public ResponseEntity<LinkResponse> registerLink(@PathVariable UUID id, @RequestBody LinkDTO payLoad) {
        Optional<Trip> trip = this.tripRepository.findById(id);

        if (trip.isPresent()) {
            Trip newTrip = trip.get();

            LinkResponse linkResponse = this.linkService.registerLink(payLoad, newTrip);

            return ResponseEntity.ok(linkResponse);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/links")
    public ResponseEntity<List<LinkData>> getAllLinks(@PathVariable UUID id) throws EventNotFoundException {
        List<LinkData> linkDataList = this.linkService.getAllActivitiesFromId(id);

        return ResponseEntity.ok(linkDataList);
    }


}
