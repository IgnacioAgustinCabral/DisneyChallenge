package com.cabral.disney.repository;

import com.cabral.disney.models.Genre;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

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

}
