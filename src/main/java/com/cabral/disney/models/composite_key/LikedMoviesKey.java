package com.cabral.disney.models.composite_key;

import lombok.Data;

import java.io.Serializable;

@Data
public class LikedMoviesKey implements Serializable {
    private Long user;
    private Long movie;
}
