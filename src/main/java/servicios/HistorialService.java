package servicios;
import model.Historial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.HistorialRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class HistorialService {

    @Autowired
    private HistorialRepository historialRepository;

    public Historial guardar(Historial historial) {
        historial.setFecha_cocinado(LocalDateTime.now());
        return historialRepository.save(historial);
    }

    public List<Historial> listarPorUsuario(int usuarioId) {
        return historialRepository.findByUsuarioId(usuarioId);
    }
}
