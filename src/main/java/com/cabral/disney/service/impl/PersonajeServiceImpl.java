package com.cabral.disney.service.impl;

import com.cabral.disney.entity.Personaje;
import com.cabral.disney.repository.PersonajeRepository;
import com.cabral.disney.service.PersonajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonajeServiceImpl implements PersonajeService {
    private PersonajeRepository personajeRepository;

    @Autowired
    public PersonajeServiceImpl(PersonajeRepository personajeRepository) {
        this.personajeRepository = personajeRepository;
    }

    @Override
    public List<Personaje> getAllPersonajes() {
        return this.personajeRepository.findAll();
    }
}
