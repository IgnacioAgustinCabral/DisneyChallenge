package com.cabral.disney.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieRequest {

    @NotBlank(message = "name is required.")
    @Size(min = 1,max = 50,message = "the title must be between {min} and {max} characters long.")
    private String titulo;

    @NotNull(message = "The creation date is required.")
    @Past(message = "The creation date must be in the past.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha_creacion;

    private Integer calificacion;
    private String imagen;
    private Set<Long> personajes;
    private Set<Long> generos;
}
