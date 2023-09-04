package com.cabral.disney.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PersonajeUpdateRequest {
    private String nombre;

    private Integer edad;

    private Double peso;

    private String historia;

    private String imagen;

    private Set<Long> peliculas;
}
