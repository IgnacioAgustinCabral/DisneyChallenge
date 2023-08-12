package com.cabral.disney.mapper;

import com.cabral.disney.dto.PersonajeDTO;
import com.cabral.disney.entity.Personaje;
import org.springframework.stereotype.Component;

@Component
public class PersonajeMapper {
    public static PersonajeDTO mapToDTO(Personaje personaje) {
        return PersonajeDTO.builder()
                .id(personaje.getId())
                .nombre(personaje.getNombre())
                .edad(personaje.getEdad())
                .peso(personaje.getPeso())
                .historia(personaje.getHistoria())
                .imagen(personaje.getImagen())
                .build();
    }

    public static Personaje mapToEntity(PersonajeDTO personajeDTO) {
        return Personaje.builder()
                .id(personajeDTO.getId())
                .nombre(personajeDTO.getNombre())
                .edad(personajeDTO.getEdad())
                .peso(personajeDTO.getPeso())
                .historia(personajeDTO.getHistoria())
                .imagen(personajeDTO.getImagen())
                .build();
    }
}
