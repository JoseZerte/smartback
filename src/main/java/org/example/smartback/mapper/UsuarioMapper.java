package org.example.smartback.mapper;

import org.example.smartback.DTOs.UsuarioDTO;
import org.example.smartback.model.Usuario;

public class UsuarioMapper {

    public static UsuarioDTO toDTO(Usuario usuario) {
        if (usuario == null) {
            return null;
        }

        UsuarioDTO dto = new UsuarioDTO();

        dto.setId(usuario.getId());
        dto.setNombre(usuario.getNombre());
        dto.setEmail(usuario.getEmail());
        dto.setAvatar(usuario.getAvatar());

        return dto;
    }

    public static Usuario toEntity(UsuarioDTO dto) {
        if (dto == null) {
            return null;
        }

        Usuario usuario = new Usuario();

        usuario.setNombre(dto.getNombre());
        usuario.setEmail(dto.getEmail());

        usuario.setContraseña(dto.getPassword());

        usuario.setAvatar(dto.getAvatar());

        return usuario;
    }
}