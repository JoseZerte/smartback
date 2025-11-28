package servicios;

import model.Ingrediente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.IngredienteRepository;

import java.util.List;

@Service
public class IngredienteService {

    @Autowired
    private IngredienteRepository ingredienteRepository;

    public Ingrediente guardar(Ingrediente ingrediente) {
        return ingredienteRepository.save(ingrediente);
    }

    public List<Ingrediente> listar() {
        return ingredienteRepository.findAll();
    }

    public Ingrediente obtenerPorId(int id) {
        return ingredienteRepository.findById(id).orElse(null);
    }

    public void eliminar(int id) {
        ingredienteRepository.deleteById(id);
    }
}

