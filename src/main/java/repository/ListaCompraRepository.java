package repository;

import model.ListaCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ListaCompraRepository extends JpaRepository<ListaCompra, Integer> {
    List<ListaCompra> findByUsuarioId(int usuarioId);
}
