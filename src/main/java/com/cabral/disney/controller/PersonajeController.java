package com.cabral.disney.controller;

import com.cabral.disney.dto.PersonajeDTO;
import com.cabral.disney.service.PersonajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<List<PersonajeDTO>> getPersonajes(){
        return new ResponseEntity<>(this.personajeService.getAllPersonajes(), HttpStatus.OK);
    }
}
