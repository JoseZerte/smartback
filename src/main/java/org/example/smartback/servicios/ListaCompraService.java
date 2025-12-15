package org.example.smartback.servicios;

import jakarta.transaction.Transactional;
import org.example.smartback.model.*;
import org.example.smartback.repository.ItemListaCompraRepository;
import org.example.smartback.repository.ListaCompraRepository;
import org.example.smartback.repository.RecetaRepository;
import org.example.smartback.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class ListaCompraService {

    @Autowired
    private ListaCompraRepository listaCompraRepository;

    @Autowired
    private RecetaRepository recetaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ItemListaCompraRepository itemListaCompraRepository;

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

    @Transactional
    public ListaCompra generarListaDesdeReceta(Integer recetaId, Integer usuarioId) {

        Optional<Receta> recetaOpt = recetaRepository.findById(recetaId);
        if (recetaOpt.isEmpty()) {
            return null;
        }
        Receta receta = recetaOpt.get();

        List<ListaCompra> listasUsuario = listaCompraRepository.findByUsuarioId(usuarioId);
        ListaCompra listaDestino;

        if (!listasUsuario.isEmpty()) {
            listaDestino = listasUsuario.get(0);
        } else {
            listaDestino = new ListaCompra();
            listaDestino.setNombre("Mi Lista de Compra");
            listaDestino.setFecha_creacion(LocalDateTime.now());

            Usuario usuario = usuarioRepository.findById(usuarioId).orElse(null);
            if (usuario == null) return null;

            listaDestino.setUsuario(usuario);
            listaDestino = listaCompraRepository.save(listaDestino);
        }

        if (listaDestino.getItems() == null) {
            listaDestino.setItems(new HashSet<>());
        }

        for (RecetaIngrediente ri : receta.getIngredientesReceta()) {

            ItemListaCompra nuevoItem = new ItemListaCompra();
            nuevoItem.setListaCompra(listaDestino);
            nuevoItem.setIngrediente(ri.getIngrediente());
            nuevoItem.setCantidad(ri.getCantidad());
            nuevoItem.setUnidad(ri.getUnidad());
            nuevoItem.setComprado(false);

            itemListaCompraRepository.save(nuevoItem);

            listaDestino.getItems().add(nuevoItem);
        }

        return listaDestino;
    }
}