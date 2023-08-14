package com.cabral.disney.service;

import com.cabral.disney.dto.PersonajeDTO;
import com.cabral.disney.entity.Personaje;
import com.cabral.disney.exception.PersonajeNotFoundException;

import java.util.List;

public interface PersonajeService {
    List<Personaje> getAllPersonajes();

    PersonajeDTO createPersonaje(PersonajeDTO personajeDTO);

    PersonajeDTO getPersonajeById(Long id) throws PersonajeNotFoundException;

    PersonajeDTO updatePersonaje(Long id, PersonajeDTO personajeDTO) throws PersonajeNotFoundException;

    void deletePersonaje(Long id) throws PersonajeNotFoundException;
}
