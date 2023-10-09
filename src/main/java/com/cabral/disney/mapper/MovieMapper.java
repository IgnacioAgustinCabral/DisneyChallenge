package com.cabral.disney.mapper;

import com.cabral.disney.models.Character;
import com.cabral.disney.models.Genre;
import com.cabral.disney.models.Movie;
import com.cabral.disney.payload.request.MovieRequest;
import com.cabral.disney.payload.response.MovieResponse;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class MovieMapper {
    public static MovieResponse mapToDTO(Movie movie) {
        MovieResponse.MovieResponseBuilder builder = MovieResponse.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .creationDate(movie.getCreationDate())
                .qualification(movie.getQualification())
                .image(movie.getImage());

        if (movie.getCharacterAssociations() != null) {
            builder.characterIds(movie.getCharacterAssociations().stream()
                    .map(character -> character.getId())
                    .collect(Collectors.toSet()));
        }

        if (movie.getGenreAssociations() != null) {
            builder.genreIds(movie.getGenreAssociations().stream()
                    .map(genre -> genre.getId())
                    .collect(Collectors.toSet()));
        }

        return builder.build();
    }

    public static Movie mapToEntity(MovieRequest request) {
        return mapToEntity(request, null, null);
    }

    public static Movie mapToEntity(MovieRequest request, Set<Character> characters, Set<Genre> genres) {
        Movie.MovieBuilder builder = Movie.builder()
                .title(request.getTitle())
                .creationDate(request.getCreationDate())
                .qualification(request.getQualification())
                .image(request.getImage());

        if (characters != null) {
            builder.characterAssociations(characters);
        }

        if (genres != null) {
            builder.genreAssociations(genres);
        }

        return builder.build();
    }
}
