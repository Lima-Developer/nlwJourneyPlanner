package com.journey.nlwjourney.link;

import com.journey.nlwjourney.exception.EventNotFoundException;
import com.journey.nlwjourney.exception.LinkAlreadyRegisteredException;
import com.journey.nlwjourney.trip.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LinkService {
    @Autowired
    private LinkRepository repository;

    // REGISTRANDO UM LINK
    public LinkResponse registerLink(LinkDTO payLoad, Trip trip) throws LinkAlreadyRegisteredException {
        Optional<Link> link = this.repository.findByUrlAndTrip(payLoad.url(), trip);

        if (link.isPresent()){
            throw new LinkAlreadyRegisteredException("Link is already registered", payLoad.url());
        }

        Link newLink = new Link(payLoad.title(), payLoad.url(), trip);
        this.repository.save(newLink);
        return new LinkResponse(newLink.getId());
    }

    // RECUPERANDO TODOS OS LINKS DE UMA VIAGEM
    public List<LinkData> getAllActivitiesFromId(UUID tripId) throws EventNotFoundException{
        List<Link> links = this.repository.findByTripId(tripId);

        if (links.isEmpty()){
            throw new EventNotFoundException("Event with id "+tripId+" não found", tripId);
        }

        return links.stream()
                .map(link -> new LinkData(link.getId(), link.getTitle(), link.getUrl()))
                .toList();
    }
}
