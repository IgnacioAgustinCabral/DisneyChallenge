package com.cabral.disney.repository;

import com.cabral.disney.models.Movie;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class MovieRepositoryTest {
    @Autowired
    private MovieRepository movieRepository;

    @Test
    public void shouldSaveMovieWithInfoAndRetrieveWithSameInfo() {
        Movie movie = Movie.builder().title("Aladin").image("path/to/image").creationDate(LocalDate.of(1994, 2, 2)).qualification(4).build();

        Movie savedMovie = this.movieRepository.save(movie);

        Optional<Movie> movieOptional = this.movieRepository.findById(savedMovie.getId());

        assertThat(movieOptional).isPresent();

        movieOptional.ifPresent(presentMovie -> {
            assertThat(presentMovie.getId()).isEqualTo(savedMovie.getId());
            assertThat(presentMovie.getTitle()).isEqualTo(savedMovie.getTitle());
            assertThat(presentMovie.getQualification()).isEqualTo(savedMovie.getQualification());
            assertThat(presentMovie.getCreationDate()).isEqualTo(savedMovie.getCreationDate());
            assertThat(presentMovie.getImage()).isEqualTo(savedMovie.getImage());
        });
    }

}
