package org.example.smartback.servicios;

import jakarta.transaction.Transactional;
import org.example.smartback.model.MeGusta;
import org.example.smartback.model.Receta;
import org.example.smartback.model.Usuario;
import org.example.smartback.repository.MeGustaRepository;
import org.example.smartback.repository.RecetaRepository;
import org.example.smartback.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MeGustaService {

    @Autowired
    private MeGustaRepository meGustaRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private RecetaRepository recetaRepository;

    @Transactional
    public String marcarComoFavorita(int usuarioId, int recetaId) {
        Usuario usuario = usuarioRepository.findById(usuarioId).orElse(null);
        Receta receta = recetaRepository.findById(recetaId).orElse(null);

        if (usuario == null || receta == null) return "No encontrado";

        if (meGustaRepository.findByUsuarioIdAndRecetaId(usuarioId, recetaId) != null) {
            return "Ya es favorita";
        }

        MeGusta favorito = new MeGusta();
        favorito.setUsuario(usuario);
        favorito.setReceta(receta);

        meGustaRepository.save(favorito);
        return "Receta marcada como favorita";
    }

    public List<MeGusta> obtenerFavoritosDeUsuario(int usuarioId) {
        return meGustaRepository.findByUsuarioId(usuarioId);
    }

    public List<Receta> obtenerRecetasFavoritasPorUsuario(int usuarioId) {
        return recetaRepository.findRecetasFavoritasByUsuarioId(usuarioId);
    }
}