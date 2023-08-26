package com.cabral.disney.repository;

import com.cabral.disney.entity.Personaje;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class PersonajeRepositoryTest {
    private Personaje personaje;

    @Autowired
    private PersonajeRepository personajeRepository;

    @BeforeEach
    public void init() {
        personaje = Personaje.builder().edad(11).historia("HISTORIA").imagen("path/imagen").nombre("nombre").peso(33.3).build();
    }

    @Test
    public void savePersonajeReturnPersonaje() {

//        Personaje createdPersonaje = em.persistAndFlush(this.personaje);
//
//        Personaje retrievedPersonaje = em.find(Personaje.class,createdPersonaje.getId());
//
//        assertThat(retrievedPersonaje.getNombre()).isEqualTo("nombre");

        Personaje createdPersonaje = this.personajeRepository.save(this.personaje);
        assertThat(createdPersonaje.getNombre()).isEqualTo("nombre");

    }
}
