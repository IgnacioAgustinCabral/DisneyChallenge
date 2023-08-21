package com.cabral.disney.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PeliculaDTO {
    private Long id;
    private String titulo;
    private LocalDate fecha_creacion;
    private Integer calificacion;
    private String imagen;
}
