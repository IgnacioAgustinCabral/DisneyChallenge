package com.cabral.disney.controller;

import com.cabral.disney.exception.CharacterNotFoundException;
import com.cabral.disney.exception.CharacterSearchEmptyResultException;
import com.cabral.disney.payload.request.CharacterRequest;
import com.cabral.disney.payload.response.CharacterResponse;
import com.cabral.disney.service.CharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> getCharacter(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(this.characterService.getCharacterById(id));
        } catch (CharacterNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }

    @GetMapping("/character")
    public ResponseEntity<?> searchCharacter(@RequestParam String name, @RequestParam Integer age) {
        try {
            return ResponseEntity.ok(this.characterService.searchCharacter(name, age));
        } catch (CharacterSearchEmptyResultException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }

    @PostMapping("/character")
    public ResponseEntity<CharacterResponse> createCharacter(@Valid @RequestBody CharacterRequest characterRequest) {
        return new ResponseEntity<>(this.characterService.createCharacter(characterRequest), HttpStatus.CREATED);
    }

    @PutMapping("/character/{id}")
    public ResponseEntity<?> updateCharacter(@PathVariable Long id, @Valid @RequestBody CharacterRequest characterRequest) {
        try {
            return ResponseEntity.ok(this.characterService.updateCharacter(id, characterRequest));
        } catch (CharacterNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }

    @DeleteMapping("/character/{id}")
    public ResponseEntity<String> deleteCharacter(@PathVariable Long id) {
        try {
            this.characterService.deleteCharacter(id);
            return ResponseEntity.noContent().build();
        } catch (CharacterNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }
}

