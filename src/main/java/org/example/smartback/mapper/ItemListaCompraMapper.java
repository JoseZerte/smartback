package org.example.smartback.mapper;

import org.example.smartback.DTOs.ItemListaCompraDTO;
import org.example.smartback.model.ItemListaCompra;
import org.example.smartback.model.Ingrediente;
import org.example.smartback.model.ListaCompra;

public class ItemListaCompraMapper {

    public static ItemListaCompraDTO toDTO(ItemListaCompra item) {
        return new ItemListaCompraDTO(
                item.getId(),
                item.getIngrediente().getId(),
                item.getCantidad(),
                item.getUnidad()
        );
    }

    public static ItemListaCompra toEntity(ItemListaCompraDTO dto, Ingrediente ingrediente, ListaCompra lista) {
        ItemListaCompra item = new ItemListaCompra();
        item.setId(dto.getId());
        item.setIngrediente(ingrediente);
        item.setListaCompra(lista);
        item.setCantidad(dto.getCantidad());
        item.setUnidad(dto.getUnidad());
        return item;
    }
}

