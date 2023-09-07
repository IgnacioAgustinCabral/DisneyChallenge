package com.cabral.disney.service.impl;

import com.cabral.disney.entity.Personaje;
import com.cabral.disney.exception.PersonajeNotFoundException;
import com.cabral.disney.exception.PersonajeSearchEmptyResultException;
import com.cabral.disney.mapper.PersonajeMapper;
import com.cabral.disney.payload.request.PersonajeRequest;
import com.cabral.disney.payload.response.PersonajeResponse;
import com.cabral.disney.repository.PersonajeRepository;
import com.cabral.disney.service.PersonajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonajeServiceImpl implements PersonajeService {
    private PersonajeRepository personajeRepository;

    @Autowired
    public PersonajeServiceImpl(PersonajeRepository personajeRepository) {
        this.personajeRepository = personajeRepository;
    }

    @Override
    public List<PersonajeResponse> getAllPersonajes() {
        List<Personaje> personajes = this.personajeRepository.findAll();
        List<PersonajeResponse> personajeResponses = personajes.stream().map(personaje -> PersonajeMapper.mapToDTO(personaje)).collect(Collectors.toList());
        return personajeResponses;
    }

    @Override
    public PersonajeResponse createPersonaje(PersonajeRequest personajeRequest) {
        Personaje newPersonaje = this.personajeRepository.save(PersonajeMapper.mapToEntity(personajeRequest));

        return PersonajeMapper.mapToDTO(newPersonaje);
    }

    @Override
    public PersonajeResponse getPersonajeById(Long id) throws PersonajeNotFoundException {
        Personaje personaje = this.personajeRepository.findById(id).orElseThrow(() -> new PersonajeNotFoundException("Personaje could not be found"));

        return PersonajeMapper.mapToDTO(personaje);
    }

    @Override
    public PersonajeResponse updatePersonaje(Long id, PersonajeRequest personajeRequest) throws PersonajeNotFoundException {
        Personaje personaje = this.personajeRepository.findById(id).orElseThrow(() -> new PersonajeNotFoundException("Personaje could not be found"));
        personaje.setEdad(personajeRequest.getEdad());
        personaje.setHistoria(personajeRequest.getHistoria());
        personaje.setImagen(personajeRequest.getImagen());
        personaje.setPeso(personajeRequest.getPeso());
        personaje.setNombre(personajeRequest.getNombre());

        Personaje savedPersonaje = this.personajeRepository.save(personaje);

        return PersonajeMapper.mapToDTO(savedPersonaje);
    }

    @Override
    public void deletePersonaje(Long id) throws PersonajeNotFoundException {
        Personaje personajeToDelete = this.personajeRepository.findById(id).orElseThrow(() -> new PersonajeNotFoundException("Personaje could not be found"));

        this.personajeRepository.delete(personajeToDelete);
    }

    @Override
    public List<PersonajeResponse> searchPersonaje(String name, Integer age) throws PersonajeSearchEmptyResultException {
        List<Personaje> personajes = this.personajeRepository.searchPersonaje(name, age);

        if(personajes.isEmpty()){
            throw new PersonajeSearchEmptyResultException("No Personaje with those parameters could be found.");
        } else {
            List<PersonajeResponse> personajeResponses = personajes.stream().map(PersonajeMapper::mapToDTO).collect(Collectors.toList());
            return personajeResponses;
        }
    }
}
