package com.journey.nlwjourney.participant;

import com.journey.nlwjourney.exception.EventNotFoundException;
import com.journey.nlwjourney.exception.ParticipantAlreadyRegisteredException;
import com.journey.nlwjourney.trip.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ParticipantService {
    @Autowired
    private ParticipantRepository repository;

    // REGISTRANDO OS PARTICIPANTES NO EVENTO
    public void registerParticipantToEvent(List<String> participantsToInvite, Trip trip) {
        List<Participant> participants = participantsToInvite.stream().map(email -> new Participant(email, trip)).toList();

        this.repository.saveAll(participants);
        System.out.println(participants.get(0).getId());
    }

    // ENVIO DE CONFIRMAÇÃO DE PARTICIPAÇÃO
    public void triggerConfirmationEmailToParticipants(UUID tripID) {

    }

    //
    public void triggerConfirmationEmailToParticipant(String email) {

    }

    // REGISTRANDO UM PARTICIPANTE EM UM EVENTO
    public ParticipantCreateResponse registerParticipantToEvent(String email, Trip trip) throws ParticipantAlreadyRegisteredException {
        Optional<Participant> existingParticipant = this.repository.findByEmailAndTrip(email,trip);

        if (existingParticipant.isPresent()){
            throw new ParticipantAlreadyRegisteredException("Participant is already registered", email, trip.getId());
        }

        Participant newParticipant = new Participant(email, trip);
        this.repository.save(newParticipant);

        return new ParticipantCreateResponse(newParticipant.getId());
    }

    // RECUPERANDO TODOS OS PARTICIPANTES DA VIAGEM
    public List<ParticipantData> getAllParticipantsFromEvent(UUID id) throws EventNotFoundException {
        List<Participant> participants = this.repository.findByTripId(id);
        if (participants.isEmpty()){
            throw new EventNotFoundException("Event with id "+id+" não found", id);
        }

        return participants.stream()
                .map(participant -> new ParticipantData(participant.getId(), participant.getName(), participant.getEmail(), participant.getIsConfirmed()))
                .toList();
    }

    public Participant confirmParticipant(UUID id) throws ParticipantNotFoundException {


        throw new ParticipantNotFoundException("", id);

    }
}
