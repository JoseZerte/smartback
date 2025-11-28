package repository;

import model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional; // ⬅️ ¡Necesitas este import para Optional!

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    // Métodos personalizados (Query Methods):
    public Usuario findByUsername(String username);
    public Usuario findByEmail(String email);
    public List<Usuario> findAllByOrderByNombreAsc();

    // --- Métodos ESTÁNDAR que habías sobrescrito (y corregidos) ---

    // 1. findById: Debe devolver Optional<Usuario> para usar .orElse()
    @Override
    public Optional<Usuario> findById(Integer id); // Usa Integer, el tipo de la clave

    // 2. save: Ya viene implícito. Si lo sobrescribes, mantiene Usuario.
    // **RECOMENDACIÓN:** Bórralo, JpaRepository ya lo incluye.
    // public Usuario save(Usuario usuario);

    // 3. deleteById: Debe devolver void, no Usuario (o puedes dejarlo implícito).
    @Override
    public void deleteById(Integer id); // Usa void, y toma Integer.

    // **NOTA:** Los métodos save y deleteById puedes borrarlos completamente,
    // ya que los heredas correctamente de JpaRepository.
}