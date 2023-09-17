package com.cabral.disney.mapper;

import com.cabral.disney.models.Character;
import com.cabral.disney.models.Movie;
import com.cabral.disney.payload.request.CharacterRequest;
import com.cabral.disney.payload.response.CharacterResponse;
import org.springframework.stereotype.Component;

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
                .history(character.getHistory())
                .image(character.getImage());

        if (character.getMovieAssociations() != null) {
            builder.movieIds(character.getMovieAssociations().stream()
                    .map(movie -> movie.getId())
                    .collect(Collectors.toSet()));
        }

        return builder.build();
    }

    public static Character mapToEntity(CharacterRequest request) {
        return mapToEntity(request, null);
    }

    public static Character mapToEntity(CharacterRequest characterRequest, Set<Movie> movies) {
        Character.CharacterBuilder builder = Character.builder()
                .name(characterRequest.getName())
                .age(characterRequest.getAge())
                .weight(characterRequest.getWeight())
                .history(characterRequest.getHistory())
                .image(characterRequest.getImage());

        if (movies != null) {
            builder.movieAssociations(movies);
        }

        return builder.build();
    }
}
