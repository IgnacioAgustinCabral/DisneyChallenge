package com.cabral.disney.service;

import com.cabral.disney.dto.PersonajeDTO;
import com.cabral.disney.exception.PersonajeNotFoundException;
import com.cabral.disney.exception.PersonajeSearchEmptyResultException;
import com.cabral.disney.payload.request.PersonajeCreateRequest;

import java.util.List;

public interface PersonajeService {
    List<PersonajeDTO> getAllPersonajes();

    PersonajeDTO createPersonaje(PersonajeCreateRequest personajeCreateRequest);

    PersonajeDTO getPersonajeById(Long id) throws PersonajeNotFoundException;

    PersonajeDTO updatePersonaje(Long id, PersonajeDTO personajeDTO) throws PersonajeNotFoundException;

    void deletePersonaje(Long id) throws PersonajeNotFoundException;

    List<PersonajeDTO> searchPersonaje(String name, Integer age) throws PersonajeSearchEmptyResultException;
}
