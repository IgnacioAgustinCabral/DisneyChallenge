package com.cabral.disney.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PersonajeDTO {
    private Long id;
    private String nombre;
    private Integer edad;
    private Double peso;
    private String historia;
    private String imagen;
}
