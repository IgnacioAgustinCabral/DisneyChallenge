package com.cabral.disney.mapper;

import com.cabral.disney.models.Genre;
import com.cabral.disney.payload.request.GenreRequest;
import com.cabral.disney.payload.response.GenreResponse;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class GenreMapper {

    public static GenreResponse mapToDTO(Genre genre) {
        GenreResponse.GenreResponseBuilder builder = GenreResponse.builder()
                .id(genre.getId())
                .name(genre.getName());

        if (genre.getMovieAssociations() != null) {
            builder.movieIds(genre.getMovieAssociations().stream()
                    .map(movie -> movie.getId())
                    .collect(Collectors.toSet()));
        }

        return builder.build();

    }

    public static Genre mapToEntity(GenreRequest request) {

        return Genre.builder().
                name(request.getName())
                .build();
    }
}
