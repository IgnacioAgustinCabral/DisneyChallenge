package com.cabral.disney.repository;

import com.cabral.disney.models.Movie;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
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

    @Test
    public void shouldReturnEmptyOptionalWhenFindingByNonExistentId() {
        Movie movie = Movie.builder().title("Aladin").image("path/to/image").creationDate(LocalDate.of(1994, 2, 2)).qualification(4).build();

        this.movieRepository.save(movie);

        Optional<Movie> movieOptional = this.movieRepository.findById(2L);

        assertThat(movieOptional).isNotPresent();
    }

    @Test
    public void shouldReturnListOfThreeMoviesWhenRetrievingAllMovies() {
        Movie movie1 = Movie.builder().title("Aladin").image("path/to/image").creationDate(LocalDate.of(1994, 2, 2)).qualification(4).build();

        Movie movie2 = Movie.builder().title("Aladin").image("path/to/image").creationDate(LocalDate.of(1994, 2, 2)).qualification(4).build();

        Movie movie3 = Movie.builder().title("Aladin").image("path/to/image").creationDate(LocalDate.of(1994, 2, 2)).qualification(4).build();
        List<Movie> moviesToSave = new LinkedList<>();
        moviesToSave.add(movie1);
        moviesToSave.add(movie2);
        moviesToSave.add(movie3);

        this.movieRepository.saveAll(moviesToSave);

        List<Movie> allMovies = this.movieRepository.findAll();

        assertThat(allMovies.size()).isEqualTo(3);
    }

    @Test
    public void shouldUpdateAMovie() {
        Movie movie = Movie.builder().title("Aladin").image("path/to/image").creationDate(LocalDate.of(1994, 2, 2)).qualification(4).build();

        Movie savedMovie = this.movieRepository.save(movie);

        Movie movieToUpdate = this.movieRepository.findById(movie.getId()).get();

        movieToUpdate.setTitle("Lion King");
        movieToUpdate.setQualification(2);

        Movie updatedMovie = this.movieRepository.save(movieToUpdate);

        assertThat(updatedMovie.getTitle()).isEqualTo("Lion King");
        assertThat(updatedMovie.getQualification()).isEqualTo(2);
        assertThat(updatedMovie.getId()).isEqualTo(savedMovie.getId());
    }
}
