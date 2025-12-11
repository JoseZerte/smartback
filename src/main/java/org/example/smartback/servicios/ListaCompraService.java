package org.example.smartback.servicios;

import org.example.smartback.model.ListaCompra;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.example.smartback.repository.ListaCompraRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ListaCompraService {

    @Autowired
    private ListaCompraRepository listaCompraRepository;

    public ListaCompra guardar(ListaCompra lista) {
        if (lista.getFecha_creacion() == null) {
            lista.setFecha_creacion(LocalDateTime.now());
        }
        return listaCompraRepository.save(lista);
    }

    public List<ListaCompra> listar() {
        return listaCompraRepository.findAll();
    }

    public List<ListaCompra> listarPorUsuario(int usuarioId) {
        return listaCompraRepository.findByUsuarioId(usuarioId);
    }

    public ListaCompra obtenerPorId(int id) {
        return listaCompraRepository.findById(id).orElse(null);
    }

    public void eliminar(int id) {
        listaCompraRepository.deleteById(id);
    }
}

