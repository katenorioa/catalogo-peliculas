package com.example.peliculas.model;

import jakarta.persistence.*;

@Entity
@Table(name = "peliculas")
public class Pelicula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(length = 1000)
    private String descripcion;

    private String imagenUrl;

    // Valores adicionales para clasificar y detallar en la BD
    private String director;
    private Integer anioLanzamiento;
    private String genero;
    private Double calificacion; // Ej. 1.0 a 10.0
    private String estadoVisualizacion; // Ej. "Pendiente", "Vista", "Favorita"

    public Pelicula() {}

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getImagenUrl() { return imagenUrl; }
    public void setImagenUrl(String imagenUrl) { this.imagenUrl = imagenUrl; }

    public String getDirector() { return director; }
    public void setDirector(String director) { this.director = director; }

    public Integer getAnioLanzamiento() { return anioLanzamiento; }
    public void setAnioLanzamiento(Integer anioLanzamiento) { this.anioLanzamiento = anioLanzamiento; }

    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }

    public Double getCalificacion() { return calificacion; }
    public void setCalificacion(Double calificacion) { this.calificacion = calificacion; }

    public String getEstadoVisualizacion() { return estadoVisualizacion; }
    public void setEstadoVisualizacion(String estadoVisualizacion) { this.estadoVisualizacion = estadoVisualizacion; }
}