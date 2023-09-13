package com.cabral.disney.mapper;

import com.cabral.disney.models.Movie;
import com.cabral.disney.payload.request.MovieRequest;
import com.cabral.disney.payload.response.MovieResponse;
import org.springframework.stereotype.Component;

@Component
public class MovieMapper {
    public static MovieResponse mapToDTO(Movie movie) {
        return MovieResponse.builder()
                .id(movie.getId())
                .imagen(movie.getImagen())
                .titulo(movie.getTitulo())
                .calificacion(movie.getCalificacion())
                .fecha_creacion(movie.getFecha_creacion())
                .build();
    }

    public static Movie mapToEntity(MovieRequest movieRequest) {
        return Movie.builder()
                .imagen(movieRequest.getImagen())
                .titulo(movieRequest.getTitulo())
                .calificacion(movieRequest.getCalificacion())
                .fecha_creacion(movieRequest.getFecha_creacion())
                .build();
    }
}
