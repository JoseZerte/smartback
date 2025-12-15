package org.example.smartback.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@Table(name = "recetas")
@NoArgsConstructor
@AllArgsConstructor
public class Receta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private String descripcion;

    @Column(nullable = false)
    private String imagen;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_categoria", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Categoria categoria;

    @Column(nullable = false)
    private String tiempo_preparacion;

    @Column(nullable = false)
    private String dificultad;

    @Column(nullable = false)
    private String tipo_dieta;

    @Column(nullable = false)
    private LocalDateTime fecha_creacion;

    @OneToMany(mappedBy = "receta", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<RecetaIngrediente> ingredientesReceta = new HashSet<>();

    @OneToMany(mappedBy = "receta", cascade = CascadeType.ALL)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Historial> historial = new HashSet<>();

    @OneToMany(mappedBy = "receta", cascade = CascadeType.ALL)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<ColeccionReceta> coleccionesRecetas = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "megusta",
            joinColumns = @JoinColumn(name = "receta_id"),
            inverseJoinColumns = @JoinColumn(name = "usuario_id")
    )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Usuario> usuariosQueGustan = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "preferencias_recetas",
            joinColumns = @JoinColumn(name = "id_receta"),
            inverseJoinColumns = @JoinColumn(name = "id_preferencia")
    )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Preferencia> preferencias = new HashSet<>();
}