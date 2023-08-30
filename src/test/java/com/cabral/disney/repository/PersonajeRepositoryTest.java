package com.cabral.disney.repository;

import com.cabral.disney.entity.Personaje;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class PersonajeRepositoryTest {
    private Personaje personaje1;
    private Personaje personaje2;

    @Autowired
    private PersonajeRepository personajeRepository;

    @BeforeEach
    public void init() {
        personaje1 = Personaje.builder().edad(11).historia("HISTORIA").imagen("path/imagen").nombre("nombre").peso(33.3).build();
        personaje2 = Personaje.builder().edad(12).historia("HISTORY").imagen("path/image").nombre("name").peso(33.3).build();
    }

    @Test
    public void savePersonajeReturnPersonaje() {

//        Personaje createdPersonaje = em.persistAndFlush(this.personaje);
//
//        Personaje retrievedPersonaje = em.find(Personaje.class,createdPersonaje.getId());
//
//        assertThat(retrievedPersonaje.getNombre()).isEqualTo("nombre");

        Personaje createdPersonaje = this.personajeRepository.save(this.personaje1);
        assertThat(createdPersonaje.getNombre()).isEqualTo("nombre");

    }

    @Test
    public void retrievePersonajeById(){
        Personaje createdPersonaje1 = this.personajeRepository.save(this.personaje1);
        Personaje createdPersonaje2 = this.personajeRepository.save(this.personaje2);

        assertThat(createdPersonaje1.getId()).isEqualTo(1L);
        assertThat(createdPersonaje2.getId()).isEqualTo(2L);
    }
}
