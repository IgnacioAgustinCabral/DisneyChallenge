package com.cabral.disney.service;

import com.cabral.disney.dto.PersonajeDTO;
import com.cabral.disney.entity.Personaje;

import java.util.List;

public interface PersonajeService {
    List<Personaje> getAllPersonajes();

    PersonajeDTO createPersonaje(PersonajeDTO personajeDTO);
}
