package com.cabral.disney.mapper;

import com.cabral.disney.models.Character;
import com.cabral.disney.models.Movie;
import com.cabral.disney.payload.request.CharacterRequest;
import com.cabral.disney.payload.response.CharacterResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CharacterMapper {
    public static CharacterResponse mapToDTO(Character character) {
        CharacterResponse.CharacterResponseBuilder builder = CharacterResponse.builder()
                .id(character.getId())
                .name(character.getName())
                .age(character.getAge())
                .weight(character.getWeight())
                .history(character.getHistory());

        if (character.getImage() != null) {
            byte[] imageBytes = Base64.getDecoder().decode(character.getImage());

            builder.imageBase64(imageBytes); // Store the binary image data in the response
        } else {
            builder.imageBase64(null);
        }

        if (character.getMovieAssociations() != null) {
            builder.movieIds(character.getMovieAssociations().stream()
                    .map(movie -> movie.getId())
                    .collect(Collectors.toSet()));
        }

        return builder.build();
    }

    public static Character mapToEntity(CharacterRequest request,MultipartFile image) {
        return mapToEntity(request, null, image);
    }

    public static Character mapToEntity(CharacterRequest characterRequest, Set<Movie> movies, MultipartFile image) {
        byte[] imageBytes = null;

        if (image != null) {
            try {
                imageBytes = Base64.getEncoder().encode(image.getBytes());
            } catch (IOException e) {
                // Handle the exception
            }
        }

        Character.CharacterBuilder builder = Character.builder()
                .name(characterRequest.getName())
                .age(characterRequest.getAge())
                .weight(characterRequest.getWeight())
                .history(characterRequest.getHistory())
                .image(imageBytes);

        if (movies != null) {
            builder.movieAssociations(movies);
        }

        return builder.build();
    }
}
