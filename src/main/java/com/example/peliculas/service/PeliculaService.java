package com.example.peliculas.service;

import com.example.peliculas.model.Pelicula;
import com.example.peliculas.repository.PeliculaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PeliculaService {

    @Autowired
    private PeliculaRepository repository;

    public List<Pelicula> listarPeliculas(String palabraClave) {
        if (palabraClave != null && !palabraClave.trim().isEmpty()) {
            return repository.findByTituloContainingIgnoreCase(palabraClave);
        }
        return repository.findAll();
    }

    public Optional<Pelicula> obtenerPorId(Long id) {
        return repository.findById(id);
    }

    public Pelicula guardar(Pelicula pelicula) {
        return repository.save(pelicula);
    }
}