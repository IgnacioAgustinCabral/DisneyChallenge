package com.cabral.disney.repository;

import com.cabral.disney.entity.Personaje;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class PersonajeRepositoryTest {
    @Autowired
    private PersonajeRepository personajeRepository;
//    @BeforeEach
//    public void init() {
//        personaje = Personaje.builder().edad(11).historia("HISTORIA").imagen("path/imagen").nombre("nombre").peso(33.3).build();
//        savedPersonaje = this.personajeRepository.save(personaje);
//    }

    @Test
    public void savePersonajeReturnPersonaje() {

//        Personaje createdPersonaje = em.persistAndFlush(this.personaje);
//
//        Personaje retrievedPersonaje = em.find(Personaje.class,createdPersonaje.getId());
//
//        assertThat(retrievedPersonaje.getNombre()).isEqualTo("nombre");
        Personaje personaje = Personaje.builder().edad(11).historia("HISTORIA").imagen("path/imagen").nombre("nombre").peso(33.3).build();
        Personaje savedPersonaje = this.personajeRepository.save(personaje);

        assertThat(savedPersonaje.getNombre()).isEqualTo("nombre");
    }

    @Test
    public void retrieveAllPersonajesReturnsEmpty(){
        List<Personaje> personajes = this.personajeRepository.findAll();

        assertThat(personajes).isEmpty();
    }
    @Test
    public void retrievePersonajeById() {
        Personaje personaje = Personaje.builder().edad(11).historia("HISTORIA").imagen("path/imagen").nombre("nombre").peso(33.3).build();

        Personaje savedPersonaje = this.personajeRepository.save(personaje);

        Personaje retrievePersonaje1 = this.personajeRepository.findById(savedPersonaje.getId()).get();

        assertThat(retrievePersonaje1.getId()).isEqualTo(savedPersonaje.getId());
    }

    @Test
    public void updatePersonaje() {
        Personaje personaje = Personaje.builder().edad(11).historia("HISTORIA").imagen("path/imagen").nombre("nombre").peso(33.3).build();

        Personaje savedPersonaje = this.personajeRepository.save(personaje);

        Personaje personajeToUpdate = this.personajeRepository.findById(savedPersonaje.getId()).get();

        //UPDATE
        personajeToUpdate.setNombre("Joaquin");
        personajeToUpdate.setEdad(33);
        personajeToUpdate.setHistoria("LA HISTORIA DE JOAQUIN");
        personajeToUpdate.setPeso(60.5);
        personajeToUpdate.setImagen("path/newImage");

        Personaje updatedPersonaje = this.personajeRepository.save(personajeToUpdate);

        assertThat(updatedPersonaje.getNombre()).isEqualTo("Joaquin");
        assertThat(updatedPersonaje.getEdad()).isEqualTo(33);
        assertThat(updatedPersonaje.getHistoria()).isEqualTo("LA HISTORIA DE JOAQUIN");
        assertThat(updatedPersonaje.getPeso()).isEqualTo(60.5);
        assertThat(updatedPersonaje.getImagen()).isEqualTo("path/newImage");
    }

    @Test
    public void deletePersonajeAndReturnsEmpty() {
        Personaje personaje = Personaje.builder().edad(11).historia("HISTORIA").imagen("path/imagen").nombre("nombre").peso(33.3).build();

        Personaje savedPersonaje = this.personajeRepository.save(personaje);

        this.personajeRepository.delete(savedPersonaje);

        // was deleted so returns empty
        Optional<Personaje> personajeOptional = this.personajeRepository.findById(savedPersonaje.getId());

        assertThat(personajeOptional).isEmpty();
    }
}
