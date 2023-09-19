package com.cabral.disney.repository;

import com.cabral.disney.models.Genre;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

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

        Genre savedGenre1 = this.genreRepository.save(genre1);

        assertThrows(DataIntegrityViolationException.class, () -> {
            this.genreRepository.save(genre2);
        });
    }

    @Test
    public void whenGenreIsSaved_ThenItCanBeRetrievedByName(){
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
}
