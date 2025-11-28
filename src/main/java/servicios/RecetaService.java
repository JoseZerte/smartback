package servicios;

import model.Receta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.RecetaRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RecetaService {

    @Autowired
    private RecetaRepository recetaRepository;

    public Receta guardar(Receta receta) {
        if (receta.getFecha_creacion() == null) {
            receta.setFecha_creacion(LocalDateTime.now());
        }
        return recetaRepository.save(receta);
    }

    public Receta obtenerPorId(int id) {
        return recetaRepository.findById(id).orElse(null);
    }

    public List<Receta> listar() {
        return recetaRepository.findAll();
    }

    public List<Receta> listarPorUsuario(int usuarioId) {
        return recetaRepository.findByUsuarioId(usuarioId);
    }

    public void eliminar(int id) {
        recetaRepository.deleteById(id);
    }
}