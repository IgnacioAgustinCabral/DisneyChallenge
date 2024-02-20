package com.cabral.disney.models.composite_key;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WatchedMovieKey implements Serializable {
    private Long user;
    private Long movie;
}
