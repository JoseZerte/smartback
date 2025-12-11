package org.example.smartback.mapper;

import org.example.smartback.DTOs.HistorialDTO;
import org.example.smartback.model.Historial;
import org.example.smartback.model.Receta;
import org.example.smartback.model.Usuario;

public class HistorialMapper {

    public static HistorialDTO toDTO(Historial historial) {
        return new HistorialDTO(
                historial.getId(),
                historial.getUsuario().getId(),
                historial.getReceta().getId(),
                historial.getFecha_cocinado(),
                historial.getComentario()
        );
    }

    public static Historial toEntity(HistorialDTO dto, Usuario usuario, Receta receta) {
        Historial historial = new Historial();
        historial.setId(dto.getId());
        historial.setUsuario(usuario);
        historial.setReceta(receta);
        historial.setFecha_cocinado(dto.getFecha_cocinado());
        historial.setComentario(dto.getComentario());
        return historial;
    }
}

