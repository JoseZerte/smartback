package org.example.smartback.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistorialDTO {
    private int id;
    private int usuarioId;
    private int recetaId;
    private LocalDateTime fecha_cocinado;
    private String comentario;
}