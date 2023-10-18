package com.cabral.disney.controller;

import com.cabral.disney.exception.CharacterNotFoundException;
import com.cabral.disney.exception.CharacterSearchEmptyResultException;
import com.cabral.disney.payload.request.CharacterRequest;
import com.cabral.disney.payload.response.CharacterResponse;
import com.cabral.disney.service.CharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/characters")
public class CharacterController {

    private CharacterService characterService;

    @Autowired
    public CharacterController(CharacterService characterService) {
        this.characterService = characterService;
    }

    @GetMapping("/character/all")
    public ResponseEntity<List<CharacterResponse>> getCharacters() {
        return new ResponseEntity<>(this.characterService.getAllCharacters(), HttpStatus.OK);
    }

    @GetMapping("/character/{id}")
    public ResponseEntity<?> getCharacter(@PathVariable Long id) throws CharacterNotFoundException {
        return ResponseEntity.ok(this.characterService.getCharacterById(id));
    }

    @GetMapping("/character")
    public ResponseEntity<?> searchCharacter(@RequestParam String name, @RequestParam Integer age) throws CharacterSearchEmptyResultException {
        return ResponseEntity.ok(this.characterService.searchCharacter(name, age));
    }

    @PostMapping("/character")
    public ResponseEntity<CharacterResponse> createCharacter(@Valid CharacterRequest characterRequest,
                                                             @RequestParam("image") MultipartFile image) {
        return new ResponseEntity<>(this.characterService.createCharacter(characterRequest, image), HttpStatus.CREATED);
    }

    @PutMapping("/character/{id}")
    public ResponseEntity<CharacterResponse> updateCharacter(@PathVariable Long id,
                                                             @Valid CharacterRequest characterRequest,
                                                             @RequestParam(name = "image") MultipartFile image
    ) throws CharacterNotFoundException {
        CharacterResponse characterResponse = characterService.updateCharacter(id, characterRequest, image);
        return ResponseEntity.ok(characterResponse);
    }

    @DeleteMapping("/character/{id}")
    public ResponseEntity<String> deleteCharacter(@PathVariable Long id) throws CharacterNotFoundException {
        this.characterService.deleteCharacter(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/character/{characterId}/image")
    public ResponseEntity<byte[]> getCharacterImage(@PathVariable Long characterId) throws CharacterNotFoundException {
        CharacterResponse character = characterService.getCharacterById(characterId);
        if (character != null && character.getImageBase64() != null) {
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG) // Adjust content type as needed
                    .body(character.getImageBase64());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

