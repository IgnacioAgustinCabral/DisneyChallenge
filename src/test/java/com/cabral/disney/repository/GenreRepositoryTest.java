package com.cabral.disney.repository;

import com.cabral.disney.models.Genre;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
public class GenreRepositoryTest {
    @Autowired
    private GenreRepository genreRepository;

    @Test
    public void savingGenreShouldReturnAGenre() {
        Genre genre = Genre.builder()
                .name("Comedy")
                .build();

        Genre savedGenre = this.genreRepository.save(genre);

        assertThat(savedGenre).isNotNull();
    }

    @Test
    public void whenGenreIsSavedWithAName_thenSavedGenreShouldHaveTheSameName() {
        Genre genre = Genre.builder()
                .name("Comedy")
                .build();

        Genre savedGenre = this.genreRepository.save(genre);

        assertThat(savedGenre.getName()).isEqualTo("Comedy");
    }

    @Test
    public void creatingGenresWithDuplicateNamesShouldThrowException() {
        Genre genre1 = Genre.builder()
                .name("Comedy")
                .build();

        Genre genre2 = Genre.builder()
                .name("Comedy")
                .build();

        this.genreRepository.save(genre1);

        assertThrows(DataIntegrityViolationException.class, () -> {
            this.genreRepository.save(genre2);
        });
    }

    @Test
    public void whenGenreIsSaved_ThenItCanBeRetrievedByName() {
        Genre genre = Genre.builder()
                .name("Comedy")
                .build();

        Genre savedGenre = this.genreRepository.save(genre);

        Optional<Genre> retrievedGenreOptional = this.genreRepository.findByName("Comedy");

        assertThat(retrievedGenreOptional).isPresent();

        retrievedGenreOptional.ifPresent(retrievedGenre -> {
            assertThat(retrievedGenre.getName()).isEqualTo(savedGenre.getName());
        });
    }

    @Test
    public void whenTryingToRetrieveNonexistentGenreByNameShouldReturnEmptyOptional() {
        Genre genre = Genre.builder()
                .name("Comedy")
                .build();

        this.genreRepository.save(genre);

        Optional<Genre> retrievedGenreOptional = this.genreRepository.findByName("Horror");

        // Assert that the retrieved optional is empty
        assertThat(retrievedGenreOptional).isNotPresent();
    }

    @Test
    public void whenSavingTwoGenres_thenRetrieveAllShouldContainBoth() {
        Genre genre1 = Genre.builder()
                .name("Comedy")
                .build();

        Genre genre2 = Genre.builder()
                .name("Romantic")
                .build();

        this.genreRepository.save(genre1);
        this.genreRepository.save(genre2);

        List<Genre> allGenres = this.genreRepository.findAll();

        assertThat(allGenres.size()).isEqualTo(2);
    }

    @Test
    public void whenGenreIsSaved_ThenRetrieveItByItsId() {
        Genre genre = Genre.builder()
                .name("Comedy")
                .build();

        Genre savedGenre = this.genreRepository.save(genre);

        Optional<Genre> retrievedGenreOptional = this.genreRepository.findById(savedGenre.getId());

        assertThat(retrievedGenreOptional).isPresent().contains(savedGenre);
    }

    @Test
    public void whenGenreIsUpdated_thenItShouldReflectTheChanges() {
        Genre genre = Genre.builder()
                .name("Comedy")
                .build();
        Genre savedGenre = this.genreRepository.save(genre);

        // Modify the genre
        savedGenre.setName("Drama");
        this.genreRepository.save(savedGenre); // Save the updated genre

        Optional<Genre> updatedGenreOptional = this.genreRepository.findById(savedGenre.getId());

        assertThat(updatedGenreOptional).isPresent();
        updatedGenreOptional.ifPresent(updatedGenre -> {
            assertThat(updatedGenre.getName()).isEqualTo("Drama");
            assertThat(updatedGenre.getId()).isEqualTo(savedGenre.getId());
        });
    }

    @Test
    public void whenGenreIsDeleted_ThenItShouldNotBeFindableById(){
        Genre genre = Genre.builder()
                .name("Comedy")
                .build();
        Genre savedGenre = this.genreRepository.save(genre);

        this.genreRepository.deleteById(savedGenre.getId());

        Optional<Genre> genreOptional = this.genreRepository.findById(savedGenre.getId());

        assertThat(genreOptional).isNotPresent();
    }
}
