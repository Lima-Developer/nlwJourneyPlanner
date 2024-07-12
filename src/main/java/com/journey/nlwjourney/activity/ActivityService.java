package com.journey.nlwjourney.activity;

import com.journey.nlwjourney.exception.ActivityAlreadyRegisteredException;
import com.journey.nlwjourney.exception.EventNotFoundException;
import com.journey.nlwjourney.trip.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ActivityService {
    @Autowired
    ActivityRepository repository;

    // REGISTRANDO UMA ACTIVITY
    public ActivityResponse registerActivity(ActivityDTO payLoad, Trip trip) throws ActivityAlreadyRegisteredException {
        Optional<Activity> activity = this.repository.findByTitleAndTrip(payLoad.title(), trip);

        if (activity.isPresent()){
            throw new ActivityAlreadyRegisteredException("Activity was already registered", payLoad.title());
        }

        Activity newActivity = new Activity(payLoad.title(), payLoad.occurs_at(), trip);
        this.repository.save(newActivity);
        return new ActivityResponse(newActivity.getId());
    }

    // RECUPERANDO TODAS AS ACTIVITIES DE UMA VIAGEM
    public List<ActivityData> getAllActivitiesFromId(UUID tripId) throws EventNotFoundException {
        List<Activity> activities = this.repository.findByTripId(tripId);

        if (activities.isEmpty()){
            throw new EventNotFoundException("Event with id "+tripId+" não found",tripId);
        }

        return activities.stream()
                .map(activity -> new ActivityData(activity.getId(), activity.getTitle(), activity.getOccursAt()))
                .toList();
    }
}
