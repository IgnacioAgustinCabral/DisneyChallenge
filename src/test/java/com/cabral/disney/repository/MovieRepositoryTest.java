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
        byte[] image = new byte[]{0x12, 0x34, 0x56, 0x78};

        Movie movie = Movie.builder()
                .title("Aladin")
                .image(image)
                .creationDate(LocalDate.of(1994, 2, 2))
                .synopsis("SYNOPSISISIS")
                .build();

        Movie savedMovie = this.movieRepository.save(movie);

        Optional<Movie> movieOptional = this.movieRepository.findById(savedMovie.getId());

        assertThat(movieOptional).isPresent();

        movieOptional.ifPresent(presentMovie -> {
            assertThat(presentMovie.getId()).isEqualTo(savedMovie.getId());
            assertThat(presentMovie.getTitle()).isEqualTo(savedMovie.getTitle());
            assertThat(presentMovie.getCreationDate()).isEqualTo(savedMovie.getCreationDate());
            assertThat(presentMovie.getImage()).isEqualTo(savedMovie.getImage());
            assertThat(presentMovie.getSynopsis()).isEqualTo(savedMovie.getSynopsis());
        });
    }

    @Test
    public void shouldReturnEmptyOptionalWhenFindingByNonExistentId() {
        byte[] image = new byte[]{0x12, 0x34, 0x56, 0x78};

        Movie movie = Movie.builder().title("Aladin").image(image).creationDate(LocalDate.of(1994, 2, 2)).synopsis("SYNOPSISISIS").build();

        this.movieRepository.save(movie);

        Optional<Movie> movieOptional = this.movieRepository.findById(2L);

        assertThat(movieOptional).isNotPresent();
    }

    @Test
    public void shouldReturnListOfThreeMoviesWhenRetrievingAllMovies() {
        byte[] image = new byte[]{0x12, 0x34, 0x56, 0x78};

        Movie movie1 = Movie.builder().title("Aladin").image(image).creationDate(LocalDate.of(1994, 2, 2)).synopsis("SYNOPSISISIS").build();

        Movie movie2 = Movie.builder().title("Aladin").image(image).creationDate(LocalDate.of(1994, 2, 2)).synopsis("SYNOPSISISIS").build();

        Movie movie3 = Movie.builder().title("Aladin").image(image).creationDate(LocalDate.of(1994, 2, 2)).synopsis("SYNOPSISISIS").build();
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
        byte[] image = new byte[]{0x12, 0x34, 0x56, 0x78};

        Movie movie = Movie.builder().title("Aladin").image(image).creationDate(LocalDate.of(1994, 2, 2)).synopsis("SYNOPSISISIS").build();

        Movie savedMovie = this.movieRepository.save(movie);

        Movie movieToUpdate = this.movieRepository.findById(movie.getId()).get();

        movieToUpdate.setTitle("Lion King");

        Movie updatedMovie = this.movieRepository.save(movieToUpdate);

        assertThat(updatedMovie.getTitle()).isEqualTo("Lion King");
        assertThat(updatedMovie.getId()).isEqualTo(savedMovie.getId());
    }

    @Test
    public void shouldDeleteAMovieByIdAndNotBeFindableById() {
        byte[] image = new byte[]{0x12, 0x34, 0x56, 0x78};

        Movie movie = Movie.builder().title("Aladin").image(image).creationDate(LocalDate.of(1994, 2, 2)).synopsis("SYNOPSISISIS").build();

        Movie savedMovie = this.movieRepository.save(movie);

        this.movieRepository.deleteById(movie.getId());

        Optional<Movie> movieOptional = this.movieRepository.findById(movie.getId());

        assertThat(movieOptional).isNotPresent();
    }

    //TODO search movie
}
