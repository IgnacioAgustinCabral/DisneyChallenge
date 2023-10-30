package com.cabral.disney.models;

import com.cabral.disney.models.composite_key.LikedMoviesKey;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(exclude = {"user", "movie"}) // Exclude user and movie fields for now
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "liked_movies")
@IdClass(LikedMoviesKey.class)
public class LikedMovies {

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @Column(name = "liked_at",nullable = false, updatable = false)
    private LocalDateTime likedAt;

}
