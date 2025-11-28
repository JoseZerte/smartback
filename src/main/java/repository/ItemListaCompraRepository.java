package repository;

import model.ItemListaCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemListaCompraRepository extends JpaRepository<ItemListaCompra, Integer> {


    List<ItemListaCompra> findByListaCompraId(int listaCompraId);


}
