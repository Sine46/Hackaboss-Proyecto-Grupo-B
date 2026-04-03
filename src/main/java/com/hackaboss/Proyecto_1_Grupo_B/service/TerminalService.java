package com.hackaboss.Proyecto_1_Grupo_B.service;

import com.hackaboss.Proyecto_1_Grupo_B.controller.TerminalController;
import com.hackaboss.Proyecto_1_Grupo_B.model.Terminal;
import com.hackaboss.Proyecto_1_Grupo_B.repository.TerminalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TerminalService {
    @Autowired
    private TerminalRepository repo;

    public List<Terminal> listarTerminales() {
        return repo.findAll();
    }

    public Terminal crearTerminal(String nombre) {
        Terminal terminal = new Terminal();
        terminal.setNombre(nombre);
        return repo.save(terminal);
    }
}
