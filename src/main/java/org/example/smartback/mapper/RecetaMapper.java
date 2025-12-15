package org.example.smartback.mapper;

import org.example.smartback.DTOs.RecetaDTO;
import org.example.smartback.model.Categoria;
import org.example.smartback.model.Receta;
import org.example.smartback.model.Usuario;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class RecetaMapper {

    public static Receta toEntity(RecetaDTO dto, Usuario usuario, Categoria categoria) {
        if (dto == null) return null;

        Receta receta = new Receta();

        if (dto.getId() != null) {
            receta.setId(dto.getId());
        }

        receta.setTitulo(dto.getTitulo());
        receta.setDescripcion(dto.getDescripcion());
        receta.setImagen(dto.getImagen());
        receta.setTiempo_preparacion(dto.getTiempoPreparacion());
        receta.setDificultad(dto.getDificultad());
        receta.setTipo_dieta(dto.getTipoDieta());

        receta.setUsuario(usuario);
        receta.setCategoria(categoria);

        return receta;
    }

    public static RecetaDTO toDTO(Receta entity) {
        if (entity == null) return null;

        RecetaDTO dto = new RecetaDTO();
        dto.setId(entity.getId());
        dto.setTitulo(entity.getTitulo());
        dto.setDescripcion(entity.getDescripcion());
        dto.setImagen(entity.getImagen());
        dto.setTiempoPreparacion(entity.getTiempo_preparacion());
        dto.setDificultad(entity.getDificultad());
        dto.setTipoDieta(entity.getTipo_dieta());
        dto.setFechaCreacion(entity.getFecha_creacion());

        if (entity.getUsuario() != null) {
            dto.setUsuarioId(entity.getUsuario().getId());
        }
        if (entity.getCategoria() != null) {
            dto.setCategoriaId(entity.getCategoria().getId());
        }

        if (entity.getIngredientesReceta() != null) {
            dto.setIngredientes(entity.getIngredientesReceta().stream()
                    .map(relacion -> {
                        RecetaDTO.IngredienteDTO iDto = new RecetaDTO.IngredienteDTO();
                        if (relacion.getIngrediente() != null) {
                            iDto.setNombre(relacion.getIngrediente().getNombre());
                        }
                        iDto.setCantidad(relacion.getCantidad());
                        iDto.setUnidad(relacion.getUnidad());
                        return iDto;
                    })
                    .collect(Collectors.toList()));
        } else {
            dto.setIngredientes(new ArrayList<>());
        }

        return dto;
    }
}