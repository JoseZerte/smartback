package org.example.smartback.servicios;
import org.example.smartback.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.example.smartback.repository.UsuarioRepository;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // crear o actualizar usuario
    public Usuario guardar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    //  usuario por id
    public Usuario obtenerPorId(int id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    // esto va a listar los usuarios
    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }

    //elimina usuario
    public void eliminar(int id) {
        usuarioRepository.deleteById(id);
    }
}


