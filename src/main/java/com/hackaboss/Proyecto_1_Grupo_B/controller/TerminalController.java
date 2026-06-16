package com.hackaboss.Proyecto_1_Grupo_B.controller;

import com.hackaboss.Proyecto_1_Grupo_B.dto.TerminalDto;
import com.hackaboss.Proyecto_1_Grupo_B.model.Terminal;
import com.hackaboss.Proyecto_1_Grupo_B.service.TerminalService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/terminales")
public class TerminalController {
    private final TerminalService terminalService;

    @GetMapping
    public ResponseEntity<List<TerminalDto>> listarTerminales() {
        return ResponseEntity.ok(terminalService.listarTerminales());
    }

    @PostMapping
    public ResponseEntity<TerminalDto> crearTerminal(@RequestBody TerminalDto terminalDto) {
        return ResponseEntity.ok(terminalService.crearTerminal(terminalDto));
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
