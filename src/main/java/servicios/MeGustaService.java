package servicios;
import model.MeGusta;
import model.Receta;
import model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.MeGustaRepository;
import repository.RecetaRepository;
import repository.UsuarioRepository;

import java.util.List;

@Service
public class MeGustaService {

    @Autowired
    private MeGustaRepository meGustaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RecetaRepository recetaRepository;

    // Marcar una receta como favorita
    public String marcarComoFavorita(int usuarioId, int recetaId) {
        Usuario usuario = usuarioRepository.findById(usuarioId).orElse(null);
        Receta receta = recetaRepository.findById(recetaId).orElse(null);

        if (usuario == null || receta == null) {
            return "Usuario o receta no encontrados";
        }

        // Verificar si ya existe
        MeGusta existing = meGustaRepository.findByUsuarioIdAndRecetaId(usuarioId, recetaId);
        if (existing != null) {
            return "Ya marcado como favorito";
        }

        // Crear favorito
        MeGusta favorito = new MeGusta();
        favorito.setUsuario(usuario);
        favorito.setReceta(receta);

        meGustaRepository.save(favorito);
        return "Receta marcada como favorita";
    }

    // Listar recetas favoritas de un usuario
    public List<MeGusta> obtenerFavoritosDeUsuario(int usuarioId) {
        return meGustaRepository.findByUsuarioId(usuarioId);
    }

    // Listar usuarios que marcaron una receta como favorita
    public List<MeGusta> obtenerUsuariosQueGustan(int recetaId) {
        return meGustaRepository.findByRecetaId(recetaId);
    }
}
