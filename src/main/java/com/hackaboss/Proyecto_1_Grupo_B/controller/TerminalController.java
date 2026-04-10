package com.hackaboss.Proyecto_1_Grupo_B.controller;

import com.hackaboss.Proyecto_1_Grupo_B.dto.TerminalDto;
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
    private TerminalService terminalService;

    @GetMapping
    public ResponseEntity<List<TerminalDto>> listarTerminales() {
        return ResponseEntity.ok(terminalService.listarTerminales());
    }

    @PostMapping
    public ResponseEntity<Terminal> crearTerminal(@RequestBody Terminal terminal) {
        return ResponseEntity.ok(terminalService.crearTerminal(terminal));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TerminalDto>actualizarTerminal(@PathVariable Long id, @RequestBody Terminal terminal){
        return ResponseEntity.ok(terminalService.actualizarTerminal(id, terminal));
    }

    @GetMapping("/mas-utilizada")
    public ResponseEntity<TerminalDto>buscarTerminalMasUtilizada(){
        return ResponseEntity.ok(terminalService.buscarTerminalMasUtilizada());
    }
}
