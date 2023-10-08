package com.cabral.disney.service;

import com.cabral.disney.exception.CharacterNotFoundException;
import com.cabral.disney.exception.CharacterSearchEmptyResultException;
import com.cabral.disney.payload.request.CharacterRequest;
import com.cabral.disney.payload.response.CharacterResponse;

import java.util.List;

public interface CharacterService {
    List<CharacterResponse> getAllCharacters();

    CharacterResponse createCharacter(CharacterRequest characterRequest);

    CharacterResponse getCharacterById(Long id) throws CharacterNotFoundException;

    CharacterResponse updateCharacter(Long id, CharacterRequest characterRequest) throws CharacterNotFoundException;

    void deleteCharacter(Long id) throws CharacterNotFoundException;

    List<CharacterResponse> searchCharacter(String name, Integer age) throws CharacterSearchEmptyResultException;
}
