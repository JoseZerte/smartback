package model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "preferencias")
@NoArgsConstructor
@AllArgsConstructor
public class Preferencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String nombre;

    @ManyToMany(mappedBy = "preferencias")
    private Set<Receta> recetas = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;
}
