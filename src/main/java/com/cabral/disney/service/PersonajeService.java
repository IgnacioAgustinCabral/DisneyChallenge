package com.cabral.disney.service;

import com.cabral.disney.dto.PersonajeDTO;
import com.cabral.disney.exception.PersonajeNotFoundException;
import com.cabral.disney.exception.PersonajeSearchEmptyResultException;
import com.cabral.disney.payload.request.PersonajeCreateRequest;
import com.cabral.disney.payload.request.PersonajeUpdateRequest;
import com.cabral.disney.payload.response.PersonajeResponse;

import java.util.List;

public interface PersonajeService {
    List<PersonajeResponse> getAllPersonajes();

    PersonajeResponse createPersonaje(PersonajeCreateRequest personajeCreateRequest);

    PersonajeResponse getPersonajeById(Long id) throws PersonajeNotFoundException;

    PersonajeResponse updatePersonaje(Long id, PersonajeUpdateRequest personajeUpdateRequest) throws PersonajeNotFoundException;

    void deletePersonaje(Long id) throws PersonajeNotFoundException;

    List<PersonajeResponse> searchPersonaje(String name, Integer age) throws PersonajeSearchEmptyResultException;
}
