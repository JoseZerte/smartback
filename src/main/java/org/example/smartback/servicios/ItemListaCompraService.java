package org.example.smartback.servicios;

import org.example.smartback.model.ItemListaCompra;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.example.smartback.repository.ItemListaCompraRepository;

import java.util.List;

@Service
public class ItemListaCompraService {

    @Autowired
    private ItemListaCompraRepository itemRepository;

    public ItemListaCompra guardar(ItemListaCompra item) {
        return itemRepository.save(item);
    }

    public List<ItemListaCompra> listarPorLista(int listaId) {
        return itemRepository.findByListaCompraId(listaId);
    }

    public void eliminar(int id) {
        itemRepository.deleteById(id);
    }
}
