package org.example.smartback.mapper;

import org.example.smartback.DTOs.MeGustaDTO;
import org.example.smartback.model.MeGusta;
import org.example.smartback.model.Receta;
import org.example.smartback.model.Usuario;

public class MeGustaMapper {

    public static MeGustaDTO toDTO(MeGusta meGusta) {
        return new MeGustaDTO(
                meGusta.getId(),
                meGusta.getUsuario().getId(),
                meGusta.getReceta().getId()
        );
    }

    public static MeGusta toEntity(MeGustaDTO dto, Usuario usuario, Receta receta) {
        MeGusta meGusta = new MeGusta();
        meGusta.setId(dto.getId());
        meGusta.setUsuario(usuario);
        meGusta.setReceta(receta);
        return meGusta;
    }
}
