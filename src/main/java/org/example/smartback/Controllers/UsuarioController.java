package org.example.smartback.Controllers;

import org.example.smartback.DTOs.UsuarioDTO;
import org.example.smartback.mapper.UsuarioMapper;
import org.example.smartback.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.example.smartback.servicios.UsuarioService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public UsuarioDTO registrar(@RequestBody UsuarioDTO usuarioDTO) {
        Usuario usuario = UsuarioMapper.toEntity(usuarioDTO);
        usuario = usuarioService.guardar(usuario);
        return UsuarioMapper.toDTO(usuario);
    }

    @GetMapping("/{id}")
    public UsuarioDTO obtener(@PathVariable int id) {
        Usuario usuario = usuarioService.obtenerPorId(id);
        return UsuarioMapper.toDTO(usuario);
    }

    @GetMapping
    public List<UsuarioDTO> listar() {
        return usuarioService.listar().stream()
                .map(UsuarioMapper::toDTO)
                .collect(Collectors.toList());
    }
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable int id) {
        usuarioService.eliminar(id);
    }
}
