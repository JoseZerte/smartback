package model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "colecciones_recetas")
@NoArgsConstructor
@AllArgsConstructor
public class ColeccionReceta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_collection")
    private Coleccion coleccion;

    @ManyToOne
    @JoinColumn(name = "id_receta")
    private Receta receta;
}
