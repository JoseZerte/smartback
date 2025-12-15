package org.example.smartback.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistorialCocinaRequest {

    private Integer recetaId;
    private Integer usuarioId;

    private LocalDateTime fechaCocina;
}