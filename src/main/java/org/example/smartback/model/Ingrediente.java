package org.example.smartback.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "ingredientes")
@NoArgsConstructor
@AllArgsConstructor
public class Ingrediente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String nombre;

    private String tipo;

    @OneToMany(mappedBy = "ingrediente", cascade = CascadeType.ALL)
    private Set<RecetaIngrediente> recetas = new HashSet<>();

    @OneToMany(mappedBy = "ingrediente", cascade = CascadeType.ALL)
    private Set<ItemListaCompra> itemsListaCompra = new HashSet<>();
}

