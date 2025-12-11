package org.example.smartback.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemListaCompraDTO {
    private int id;
    private int ingredienteId;
    private int cantidad;
    private String unidad;
}
