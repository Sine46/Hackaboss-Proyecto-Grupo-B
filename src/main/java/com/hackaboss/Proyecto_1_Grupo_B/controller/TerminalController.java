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
        return ResponseEntity.ok(service.listarTerminales());
    }

    @PostMapping
    public ResponseEntity<Terminal> crearTerminal(@RequestBody Terminal terminal){
        return ResponseEntity.ok(service.crearTerminal(terminal));
    }
}
