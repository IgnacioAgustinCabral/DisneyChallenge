package com.cabral.disney.mapper;

import com.cabral.disney.dto.PersonajeDTO;
import com.cabral.disney.entity.Personaje;
import com.cabral.disney.payload.request.PersonajeCreateRequest;
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

    public static Personaje mapToEntity(PersonajeCreateRequest personajeCreateRequest) {
        return Personaje.builder()
                .nombre(personajeCreateRequest.getNombre())
                .edad(personajeCreateRequest.getEdad())
                .peso(personajeCreateRequest.getPeso())
                .historia(personajeCreateRequest.getHistoria())
                .imagen(personajeCreateRequest.getImagen())
                .build();
    }
}
