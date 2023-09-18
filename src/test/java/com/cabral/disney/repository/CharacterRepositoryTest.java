package com.cabral.disney.repository;

import com.cabral.disney.models.Character;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class CharacterRepositoryTest {
    @Autowired
    private CharacterRepository characterRepository;

    @Test
    public void saveCharacterReturnCharacter() {

//        Character createdCharacter = em.persistAndFlush(this.personaje);
//
//        Character retrievedCharacter = em.find(Character.class,createdCharacter.getId());
//
//        assertThat(retrievedCharacter.getName()()).isEqualTo("name");
        Character character = Character.builder().age(11).history("HISTORIA").image("path/image").name("name").weight(33.3).build();
        Character savedCharacter = this.characterRepository.save(character);

        assertThat(savedCharacter.getName()).isEqualTo("name");
    }

    @Test
    public void retrieveAllCharactersReturnsEmpty() {
        List<Character> characters = this.characterRepository.findAll();

        assertThat(characters).isEmpty();
    }

    @Test
    public void retrieveAllCharactersReturnListIsSize2() {
        Character character1 = Character.builder().age(11).history("HISTORIA").image("path/image").name("name").weight(33.3).build();
        Character savedCharacter1 = this.characterRepository.save(character1);

        Character character2 = Character.builder().age(11).history("HISTORIA").image("path/image").name("name").weight(33.3).build();
        Character savedCharacter2 = this.characterRepository.save(character2);

        List<Character> characters = this.characterRepository.findAll();

        assertThat(characters.size()).isEqualTo(2);
    }

    @Test
    public void retrieveCharacterById() {
        Character character = Character.builder().age(11).history("HISTORIA").image("path/image").name("name").weight(33.3).build();

        Character savedCharacter = this.characterRepository.save(character);

        Character retrieveCharacter1 = this.characterRepository.findById(savedCharacter.getId()).get();

        assertThat(retrieveCharacter1.getId()).isEqualTo(savedCharacter.getId());
    }

    @Test
    public void updateCharacter() {
        Character character = Character.builder().age(11).history("HISTORIA").image("path/image").name("name").weight(33.3).build();

        Character savedCharacter = this.characterRepository.save(character);

        Character characterToUpdate = this.characterRepository.findById(savedCharacter.getId()).get();

        //UPDATE
        characterToUpdate.setName("Joaquin");
        characterToUpdate.setAge(33);
        characterToUpdate.setHistory("LA HISTORIA DE JOAQUIN");
        characterToUpdate.setWeight(60.5);
        characterToUpdate.setImage("path/newImage");

        Character updatedCharacter = this.characterRepository.save(characterToUpdate);

        assertThat(updatedCharacter.getName()).isEqualTo("Joaquin");
        assertThat(updatedCharacter.getAge()).isEqualTo(33);
        assertThat(updatedCharacter.getHistory()).isEqualTo("LA HISTORIA DE JOAQUIN");
        assertThat(updatedCharacter.getWeight()).isEqualTo(60.5);
        assertThat(updatedCharacter.getImage()).isEqualTo("path/newImage");
    }

    @Test
    public void deleteCharacterAndReturnsEmpty() {
        Character character = Character.builder().age(11).history("HISTORIA").image("path/image").name("name").weight(33.3).build();

        Character savedCharacter = this.characterRepository.save(character);

        this.characterRepository.delete(savedCharacter);

        // was deleted so returns empty
        Optional<Character> characterOptional = this.characterRepository.findById(savedCharacter.getId());

        assertThat(characterOptional).isEmpty();
    }

    @Test
    public void searchCharacterByNombreAndAgeIsNotEmpty() {
        Character character = Character.builder().age(11).history("HISTORIA").image("path/image").name("name").weight(33.3).build();

        Character savedCharacter = this.characterRepository.save(character);

        List<Character> characters = this.characterRepository.searchCharacter(savedCharacter.getName(), savedCharacter.getAge());

        assertThat(characters).isNotEmpty();
    }

    @Test
    public void searchCharacterOnlyByNombreIsEmpty() {
        Character character = Character.builder().age(11).history("HISTORIA").image("path/image").name("name").weight(33.3).build();

        Character savedCharacter = this.characterRepository.save(character);

        List<Character> characters = this.characterRepository.searchCharacter(savedCharacter.getName(), null);

        assertThat(characters).isEmpty();
    }

}
