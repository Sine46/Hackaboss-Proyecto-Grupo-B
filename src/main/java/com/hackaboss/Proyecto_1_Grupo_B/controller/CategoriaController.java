package com.hackaboss.Proyecto_1_Grupo_B.controller;

import com.hackaboss.Proyecto_1_Grupo_B.model.Categoria;
import com.hackaboss.Proyecto_1_Grupo_B.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {
    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public ResponseEntity<List<Categoria>> listarCategorias(){
        return ResponseEntity.ok(categoriaService.listarCategorias());
    }

    @PostMapping
    public ResponseEntity<Categoria> crearCategoria(@RequestBody Categoria categoria){
        return ResponseEntity.ok(categoriaService.crearCategoria(categoria));
    }
}
