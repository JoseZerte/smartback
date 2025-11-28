package servicios;
import java.util.List;
import java.util.Optional;
import model.Categoria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.CategoriaRepository;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public Categoria guardar(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    public List<Categoria> listar() {
        return categoriaRepository.findAll();
    }

    public Categoria obtenerPorId(int id) {
        return categoriaRepository.findById(id).orElse(null);
    }

    public void eliminar(int id) {
        categoriaRepository.deleteById(id);
    }
}

