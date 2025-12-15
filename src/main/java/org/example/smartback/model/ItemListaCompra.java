package org.example.smartback.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@Table(name = "items_lista_compra")
@NoArgsConstructor
@AllArgsConstructor
public class ItemListaCompra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_lista")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private ListaCompra listaCompra;

    @ManyToOne
    @JoinColumn(name = "id_ingrediente")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Ingrediente ingrediente;

    private int cantidad;

    private String unidad;

    @Column(columnDefinition = "boolean default false")
    private boolean comprado;
}