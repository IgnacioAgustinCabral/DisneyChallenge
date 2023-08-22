package com.cabral.disney.mapper;

import com.cabral.disney.dto.PeliculaDTO;
import com.cabral.disney.entity.Pelicula;
import org.springframework.stereotype.Component;

@Component
public class PeliculaMapper {
    public static PeliculaDTO mapToDTO(Pelicula pelicula) {
        return PeliculaDTO.builder()
                .id(pelicula.getId())
                .imagen(pelicula.getImagen())
                .titulo(pelicula.getTitulo())
                .calificacion(pelicula.getCalificacion())
                .fecha_creacion(pelicula.getFecha_creacion())
                .build();
    }

    public static Pelicula mapToEntity(PeliculaDTO peliculaDTO){
        return Pelicula.builder()
                .id(peliculaDTO.getId())
                .imagen(peliculaDTO.getImagen())
                .titulo(peliculaDTO.getTitulo())
                .calificacion(peliculaDTO.getCalificacion())
                .fecha_creacion(peliculaDTO.getFecha_creacion())
                .build();
    }
}
