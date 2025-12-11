package org.example.smartback.mapper;

import org.example.smartback.DTOs.RecetaDTO;
import org.example.smartback.model.Categoria;
import org.example.smartback.model.Receta;
import org.example.smartback.model.Usuario;

public class RecetaMapper {

    public static RecetaDTO toDTO(Receta receta) {
        return new RecetaDTO(
                receta.getId(),
                receta.getTitulo(),
                receta.getDescripcion(),
                receta.getImagen(),
                receta.getTiempo_preparacion(),
                receta.getDificultad(),
                receta.getTipo_dieta(),
                receta.getFecha_creacion(),
                receta.getUsuario().getId(),
                receta.getCategoria().getId()
        );
    }

    public static Receta toEntity(RecetaDTO dto, Usuario usuario, Categoria categoria) {
        Receta receta = new Receta();
        receta.setId(dto.getId());
        receta.setTitulo(dto.getTitulo());
        receta.setDescripcion(dto.getDescripcion());
        receta.setImagen(dto.getImagen());
        receta.setTiempo_preparacion(dto.getTiempo_preparacion());
        receta.setDificultad(dto.getDificultad());
        receta.setTipo_dieta(dto.getTipo_dieta());
        receta.setFecha_creacion(dto.getFecha_creacion());
        receta.setUsuario(usuario);
        receta.setCategoria(categoria);
        return receta;
    }
}
