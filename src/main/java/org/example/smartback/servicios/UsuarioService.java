package org.example.smartback.servicios;

import org.example.smartback.model.Usuario;
import org.example.smartback.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public Usuario guardar(Usuario usuario) {

        String contrasenaPlana = usuario.getContraseña();

        if (contrasenaPlana != null && !contrasenaPlana.startsWith("$2a$")) {

            String contrasenaCifrada = passwordEncoder.encode(contrasenaPlana);

            usuario.setContraseña(contrasenaCifrada);
        } else if (contrasenaPlana == null || contrasenaPlana.isEmpty()) {
        }

        return usuarioRepository.save(usuario);
    }

    public Usuario obtenerPorId(int id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }

    public void eliminar(int id) {
        usuarioRepository.deleteById(id);
    }
}