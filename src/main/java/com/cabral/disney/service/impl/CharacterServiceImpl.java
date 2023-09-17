package com.cabral.disney.service.impl;

import com.cabral.disney.exception.CharacterNotFoundException;
import com.cabral.disney.exception.CharacterSearchEmptyResultException;
import com.cabral.disney.exception.MovieNotFoundException;
import com.cabral.disney.mapper.CharacterMapper;
import com.cabral.disney.models.Character;
import com.cabral.disney.models.Movie;
import com.cabral.disney.payload.request.CharacterRequest;
import com.cabral.disney.payload.response.CharacterResponse;
import com.cabral.disney.repository.CharacterRepository;
import com.cabral.disney.repository.MovieRepository;
import com.cabral.disney.service.CharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CharacterServiceImpl implements CharacterService {
    private CharacterRepository characterRepository;
    private MovieRepository movieRepository;

    @Autowired
    public CharacterServiceImpl(CharacterRepository characterRepository, MovieRepository movieRepository) {
        this.characterRepository = characterRepository;
        this.movieRepository = movieRepository;
    }

    @Override
    public List<CharacterResponse> getAllCharacters() {
        List<Character> characters = this.characterRepository.findAll();
        List<CharacterResponse> characterResponses = characters.stream().map(character -> CharacterMapper.mapToDTO(character)).collect(Collectors.toList());
        return characterResponses;
    }

    @Override
    public CharacterResponse createCharacter(CharacterRequest characterRequest) {
        Set<Movie> movies = null;
        Set<Long> movieIds = characterRequest.getMovieIds();

        if (movieIds != null) {
            movies = movieIds.stream().map(id -> {
                        try {
                            return this.movieRepository.findById(id).orElseThrow(() -> new MovieNotFoundException("Movie not found with id: " + id));
                        } catch (MovieNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .collect(Collectors.toSet());
        }

        Character newCharacter = CharacterMapper.mapToEntity(characterRequest,movies);
        Character savedCharacter = this.characterRepository.save(newCharacter);

        return CharacterMapper.mapToDTO(savedCharacter);
    }

    @Override
    public CharacterResponse getCharacterById(Long id) throws CharacterNotFoundException {
        Character character = this.characterRepository.findById(id).orElseThrow(() -> new CharacterNotFoundException("Character could not be found"));

        return CharacterMapper.mapToDTO(character);
    }

    @Override
    public CharacterResponse updateCharacter(Long id, CharacterRequest characterRequest) throws CharacterNotFoundException {
        Character character = this.characterRepository.findById(id).orElseThrow(() -> new CharacterNotFoundException("Character could not be found"));
        character.setAge(characterRequest.getAge());
        character.setHistory(characterRequest.getHistory());
        character.setImage(characterRequest.getImage());
        character.setWeight(characterRequest.getWeight());
        character.setName(characterRequest.getName());

        Character savedCharacter = this.characterRepository.save(character);

        return CharacterMapper.mapToDTO(savedCharacter);
    }

    @Override
    public void deleteCharacter(Long id) throws CharacterNotFoundException {
        Character characterToDelete = this.characterRepository.findById(id).orElseThrow(() -> new CharacterNotFoundException("Character could not be found"));

        this.characterRepository.delete(characterToDelete);
    }

    @Override
    public List<CharacterResponse> searchCharacter(String name, Integer age) throws CharacterSearchEmptyResultException {
        List<Character> characters = this.characterRepository.searchCharacter(name, age);

        if (characters.isEmpty()) {
            throw new CharacterSearchEmptyResultException("No Character with those parameters could be found.");
        } else {
            List<CharacterResponse> characterResponses = characters.stream().map(CharacterMapper::mapToDTO).collect(Collectors.toList());
            return characterResponses;
        }
    }
}
