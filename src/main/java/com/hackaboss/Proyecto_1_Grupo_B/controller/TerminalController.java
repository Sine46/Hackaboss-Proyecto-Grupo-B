package com.hackaboss.Proyecto_1_Grupo_B.controller;

import com.hackaboss.Proyecto_1_Grupo_B.model.Terminal;
import com.hackaboss.Proyecto_1_Grupo_B.service.TerminalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/terminales")
public class TerminalController {
    @Autowired
    private TerminalService service;

    @GetMapping
    public ResponseEntity<List<Terminal>> listarTerminales(){
        if(service.listarTerminales().isEmpty()) return ResponseEntity.notFound().build();
        else return ResponseEntity.ok(service.listarTerminales());
    }

    @PostMapping
    public ResponseEntity<Terminal> crearTerminal(@RequestBody String nombre){
        if (nombre.isBlank() || nombre.equals(null)) return ResponseEntity.badRequest().build();
        else return ResponseEntity.ok(service.crearTerminal(nombre));
    }
}
