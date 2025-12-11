package org.example.smartback.mapper;

import org.example.smartback.DTOs.UsuarioDTO;
import org.example.smartback.model.Usuario;

public class UsuarioMapper {

    public static UsuarioDTO toDTO(Usuario usuario) {
        return new UsuarioDTO(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getEmail(),
                usuario.getAvatar(),
                usuario.getFecha_registr()
        );
    }

    public static Usuario toEntity(UsuarioDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setId(dto.getId());
        usuario.setNombre(dto.getNombre());
        usuario.setApellido(dto.getApellido());
        usuario.setEmail(dto.getEmail());
        usuario.setAvatar(dto.getAvatar());
        usuario.setFecha_registr(dto.getFecha_registr());
        return usuario;
    }
}
