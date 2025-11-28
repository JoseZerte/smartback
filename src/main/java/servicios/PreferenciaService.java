package servicios;

import model.Preferencia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.PreferenciaRepository;

import java.util.List;

@Service
public class PreferenciaService {

    @Autowired
    private PreferenciaRepository preferenciaRepository;

    public Preferencia guardar(Preferencia preferencia) {
        return preferenciaRepository.save(preferencia);
    }

    public List<Preferencia> listar() {
        return preferenciaRepository.findAll();
    }

    public void eliminar(int id) {
        preferenciaRepository.deleteById(id);
    }
}
