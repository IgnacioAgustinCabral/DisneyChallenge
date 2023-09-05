package com.cabral.disney.mapper;

import com.cabral.disney.entity.Personaje;
import com.cabral.disney.payload.request.PersonajeRequest;
import com.cabral.disney.payload.response.PersonajeResponse;
import org.springframework.stereotype.Component;

@Component
public class PersonajeMapper {
    public static PersonajeResponse mapToDTO(Personaje personaje) {
        return PersonajeResponse.builder()
                .id(personaje.getId())
                .nombre(personaje.getNombre())
                .edad(personaje.getEdad())
                .peso(personaje.getPeso())
                .historia(personaje.getHistoria())
                .imagen(personaje.getImagen())
                .build();
    }

    public static Personaje mapToEntity(PersonajeRequest personajeRequest) {
        return Personaje.builder()
                .nombre(personajeRequest.getNombre())
                .edad(personajeRequest.getEdad())
                .peso(personajeRequest.getPeso())
                .historia(personajeRequest.getHistoria())
                .imagen(personajeRequest.getImagen())
                .build();
    }
}
