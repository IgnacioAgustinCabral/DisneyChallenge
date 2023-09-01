package com.cabral.disney.controller;

import com.cabral.disney.dto.PersonajeDTO;
import com.cabral.disney.exception.PersonajeNotFoundException;
import com.cabral.disney.exception.PersonajeSearchEmptyResultException;
import com.cabral.disney.service.PersonajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/personajes")
public class PersonajeController {

    private PersonajeService personajeService;

    @Autowired
    public PersonajeController(PersonajeService personajeService) {
        this.personajeService = personajeService;
    }

    @GetMapping("/personaje/all")
    public ResponseEntity<List<PersonajeDTO>> getPersonajes() {
        return new ResponseEntity<>(this.personajeService.getAllPersonajes(), HttpStatus.OK);
    }

    @GetMapping("/personaje/{id}")
    public ResponseEntity<?> getPersonaje(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(this.personajeService.getPersonajeById(id));
        } catch (PersonajeNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }

    @GetMapping("/personaje")
    public ResponseEntity<?> searchPersonaje(@RequestParam String name, @RequestParam Integer age) {
        try {
            return ResponseEntity.ok(this.personajeService.searchPersonaje(name, age));
        } catch (PersonajeSearchEmptyResultException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }


    @PutMapping("/personaje/{id}")
    public ResponseEntity<?> updatePersonaje(@PathVariable Long id, @RequestBody PersonajeDTO personaje) {
        try {
            return ResponseEntity.ok(this.personajeService.updatePersonaje(id, personaje));
        } catch (PersonajeNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }

    @PostMapping("/personaje")
    public ResponseEntity<PersonajeDTO> createPersonaje(@RequestBody PersonajeDTO personajeDTO) {
        return new ResponseEntity<>(this.personajeService.createPersonaje(personajeDTO), HttpStatus.CREATED);
    }

    @DeleteMapping("/personaje/{id}")
    public ResponseEntity<String> deletePersonaje(@PathVariable Long id) {
        try {
            this.personajeService.deletePersonaje(id);
            return ResponseEntity.noContent().build();
        } catch (PersonajeNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }
}

