package com.cabral.disney.mapper;

import com.cabral.disney.models.Movie;
import com.cabral.disney.models.Personaje;
import com.cabral.disney.payload.request.MovieRequest;
import com.cabral.disney.payload.response.MovieResponse;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class MovieMapper {
    public static MovieResponse mapToDTO(Movie movie) {
        return MovieResponse.builder()
                .id(movie.getId())
                .imagen(movie.getImagen())
                .titulo(movie.getTitulo())
                .calificacion(movie.getCalificacion())
                .fecha_creacion(movie.getFecha_creacion())
                .personajeIds(
                        movie.getPersonajes_asociados()
                                .stream()
                                .map(personaje -> personaje.getId())
                                .collect(Collectors.toSet())
                )
                .build();
    }

    public static Movie mapToEntity(MovieRequest movieRequest, Set<Personaje> personajes) {
        return Movie.builder()
                .imagen(movieRequest.getImagen())
                .titulo(movieRequest.getTitulo())
                .calificacion(movieRequest.getCalificacion())
                .fecha_creacion(movieRequest.getFecha_creacion())
                .personajes_asociados(personajes)
                .build();
    }
}
