package com.example.peliculas.controller;

import com.example.peliculas.model.Pelicula;
import com.example.peliculas.service.PeliculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Controller
@RequestMapping("/peliculas")
public class PeliculaController {

    @Autowired
    private PeliculaService service;

    private final Path rootLocation = Paths.get("uploads");

    @GetMapping
    public String listar(@RequestParam(name = "buscar", required = false) String buscar, Model model) {
        model.addAttribute("peliculas", service.listarPeliculas(buscar));
        model.addAttribute("buscar", buscar);
        return "index";
    }

    @GetMapping("/nueva")
    public String mostrarFormularioNueva(Model model) {
        model.addAttribute("pelicula", new Pelicula());
        return "nueva";
    }

    // Guardar nueva película con archivo de imagen subido por el usuario
    @PostMapping("/guardar")
    public String guardarPelicula(@ModelAttribute Pelicula pelicula,
                                  @RequestParam("archivoImagen") MultipartFile archivoImagen) {
        if (!archivoImagen.isEmpty()) {
            String rutaImagen = guardarArchivo(archivoImagen);
            pelicula.setImagenUrl(rutaImagen);
        }
        service.guardar(pelicula);
        return "redirect:/peliculas";
    }

    @GetMapping("/detalle/{id}")
    public String verDetalle(@PathVariable Long id, Model model) {
        Pelicula pelicula = service.obtenerPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));
        model.addAttribute("pelicula", pelicula);
        return "detalle";
    }

    // Actualizar detalles (conserva la imagen anterior si el usuario no sube una nueva)
    @PostMapping("/actualizar/{id}")
    public String actualizarDetalles(@PathVariable Long id,
                                     @ModelAttribute Pelicula peliculaActualizada,
                                     @RequestParam(value = "archivoImagen", required = false) MultipartFile archivoImagen) {
        Pelicula existente = service.obtenerPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));

        peliculaActualizada.setId(id);

        if (archivoImagen != null && !archivoImagen.isEmpty()) {
            String nuevaRuta = guardarArchivo(archivoImagen);
            peliculaActualizada.setImagenUrl(nuevaRuta);
        } else {
            peliculaActualizada.setImagenUrl(existente.getImagenUrl());
        }

        service.guardar(peliculaActualizada);
        return "redirect:/peliculas/detalle/" + id + "?exito";
    }

    // Método auxiliar que guarda la imagen en la carpeta 'uploads'
    private String guardarArchivo(MultipartFile archivo) {
        try {
            if (!Files.exists(rootLocation)) {
                Files.createDirectories(rootLocation);
            }
            String nombreUnico = UUID.randomUUID().toString() + "_" + archivo.getOriginalFilename();
            Path destino = this.rootLocation.resolve(nombreUnico);
            Files.copy(archivo.getInputStream(), destino, StandardCopyOption.REPLACE_EXISTING);
            return "/uploads/" + nombreUnico;
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar la imagen subida: " + e.getMessage());
        }
    }
}