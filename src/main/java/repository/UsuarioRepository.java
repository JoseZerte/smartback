package repository;

import model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    public Usuario findByUsername(String username);
    public Usuario findByEmail(String email);
    public List<Usuario> findAllByOrderByNombreAsc();
    public Usuario findById(int id);
    public Usuario save(Usuario usuario);
    public Usuario deleteById(int id);
}
