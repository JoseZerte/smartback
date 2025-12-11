package org.example.smartback.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecetaDTO {
    private int id;
    private String titulo;
    private String descripcion;
    private String imagen;
    private String tiempo_preparacion;
    private String dificultad;
    private String tipo_dieta;
    private LocalDateTime fecha_creacion;
    private int usuarioId;
    private int categoriaId;
}

