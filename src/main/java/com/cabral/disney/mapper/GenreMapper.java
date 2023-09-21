package com.cabral.disney.mapper;

import com.cabral.disney.models.Genre;
import com.cabral.disney.payload.response.GenreResponse;

import java.util.stream.Collectors;

public class GenreMapper {

    public static GenreResponse mapToDTO(Genre genre) {
        return GenreResponse.builder()
                .id(genre.getId())
                .name(genre.getName())
                .movieIds(
                        genre.getMovieAssociations().stream()
                                .map(movie -> movie.getId())
                                .collect(Collectors.toSet()))
                .build();

    }
}
