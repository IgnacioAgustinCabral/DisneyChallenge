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
    @Size(min = 1,max = 300,message = "the name must be between {min} and {max} characters long.")
    private String nombre;

    @Min(value = 1, message = "age must be greater or equal than {min}")
    private Integer edad;

    @DecimalMin(value = "0.1",message = "weight must be greater or equal than {value}")
    private Double peso;

    @Size(min = 1,max = 2000,message = "the history must be between {min} and {max} characters long.")
    private String historia;

    private String imagen;

    private Set<Long> peliculas;
}
