package com.cabral.disney.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieResponse {
    private Long id;
    private String titulo;
    private LocalDate fecha_creacion;
    private Integer calificacion;
    private String imagen;
    private Set<Long> personajeIds;
    private Set<Long> generos;
}
