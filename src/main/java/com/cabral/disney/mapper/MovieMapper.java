package com.cabral.disney.mapper;

import com.cabral.disney.models.Character;
import com.cabral.disney.models.Genre;
import com.cabral.disney.models.Movie;
import com.cabral.disney.payload.request.MovieRequest;
import com.cabral.disney.payload.response.MovieResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class MovieMapper {
    public static MovieResponse mapToDTO(Movie movie) {
        MovieResponse.MovieResponseBuilder builder = MovieResponse.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .creationDate(movie.getCreationDate())
                .synopsis(movie.getSynopsis());

        if (movie.getImage() != null) {
            byte[] imageBytes = Base64.getDecoder().decode(movie.getImage());

            builder.image(imageBytes); // Store the binary image data in the response
        } else {
            builder.image(null);
        }

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

    public static Movie mapToEntity(MovieRequest request, MultipartFile file) {
        return mapToEntity(request, null, null, file);
    }

    public static Movie mapToEntity(MovieRequest request, Set<Character> characters, Set<Genre> genres, MultipartFile file) {
        Movie.MovieBuilder builder = Movie.builder()
                .title(request.getTitle())
                .creationDate(request.getCreationDate())
                .synopsis(request.getSynopsis());

        if (file != null) {
            try {
                builder.image(Base64.getEncoder().encode(file.getBytes()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if (characters != null) {
            builder.characterAssociations(characters);
        }

        if (genres != null) {
            builder.genreAssociations(genres);
        }

        return builder.build();
    }
}
