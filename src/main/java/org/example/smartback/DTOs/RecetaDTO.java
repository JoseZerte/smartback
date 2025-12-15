package org.example.smartback.DTOs;

import java.time.LocalDateTime;
import java.util.List;

public class RecetaDTO {
    private Integer id;
    private String titulo;
    private String descripcion;
    private String imagen;
    private String tiempoPreparacion;
    private String dificultad;
    private String tipoDieta;
    private LocalDateTime fechaCreacion;
    private Integer usuarioId;
    private Integer categoriaId;

    private List<IngredienteDTO> ingredientes;

    public RecetaDTO() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public String getImagen() { return imagen; }
    public void setImagen(String imagen) { this.imagen = imagen; }
    public String getTiempoPreparacion() { return tiempoPreparacion; }
    public void setTiempoPreparacion(String tiempoPreparacion) { this.tiempoPreparacion = tiempoPreparacion; }
    public String getDificultad() { return dificultad; }
    public void setDificultad(String dificultad) { this.dificultad = dificultad; }
    public String getTipoDieta() { return tipoDieta; }
    public void setTipoDieta(String tipoDieta) { this.tipoDieta = tipoDieta; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
    public Integer getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Integer usuarioId) { this.usuarioId = usuarioId; }
    public Integer getCategoriaId() { return categoriaId; }
    public void setCategoriaId(Integer categoriaId) { this.categoriaId = categoriaId; }

    public List<IngredienteDTO> getIngredientes() { return ingredientes; }
    public void setIngredientes(List<IngredienteDTO> ingredientes) { this.ingredientes = ingredientes; }

    public static class IngredienteDTO {
        private String nombre;
        private int cantidad;
        private String unidad;

        public IngredienteDTO() {}

        public String getNombre() { return nombre; }
        public void setNombre(String nombre) { this.nombre = nombre; }
        public int getCantidad() { return cantidad; }
        public void setCantidad(int cantidad) { this.cantidad = cantidad; }
        public String getUnidad() { return unidad; }
        public void setUnidad(String unidad) { this.unidad = unidad; }
    }
}