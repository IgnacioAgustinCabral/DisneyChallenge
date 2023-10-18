package com.cabral.disney.service;

import com.cabral.disney.exception.CharacterNotFoundException;
import com.cabral.disney.exception.CharacterSearchEmptyResultException;
import com.cabral.disney.payload.request.CharacterRequest;
import com.cabral.disney.payload.response.CharacterResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CharacterService {
    List<CharacterResponse> getAllCharacters();
    CharacterResponse createCharacter(CharacterRequest characterRequest, MultipartFile image);

    CharacterResponse getCharacterById(Long id) throws CharacterNotFoundException;

    CharacterResponse updateCharacter(Long id, CharacterRequest characterRequest, MultipartFile image) throws CharacterNotFoundException;

    void deleteCharacter(Long id) throws CharacterNotFoundException;

    List<CharacterResponse> searchCharacter(String name, Integer age) throws CharacterSearchEmptyResultException;
}
