package com.cabral.disney.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PersonajeResponse {
    private Long id;

    private String nombre;

    private Integer edad;

    private Double peso;

    private String historia;

    private String imagen;

    private Set<Long> peliculaIds;
}
