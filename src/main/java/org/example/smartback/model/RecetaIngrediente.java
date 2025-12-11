package org.example.smartback.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "ingredientes_receta")
@NoArgsConstructor
@AllArgsConstructor
public class RecetaIngrediente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_receta")
    private Receta receta;

    @ManyToOne
    @JoinColumn(name = "id_ingrediente")
    private Ingrediente ingrediente;

    private int cantidad;

    private String unidad;
}
