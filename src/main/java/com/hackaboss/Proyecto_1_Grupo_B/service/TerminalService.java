package com.hackaboss.Proyecto_1_Grupo_B.service;

import com.hackaboss.Proyecto_1_Grupo_B.exception.DatosNoValidosException;
import com.hackaboss.Proyecto_1_Grupo_B.model.Terminal;
import com.hackaboss.Proyecto_1_Grupo_B.repository.TerminalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TerminalService {
    @Autowired
    private TerminalRepository terminalRepository;

    public List<Terminal> listarTerminales() {
        return terminalRepository.findAll();
    }

    public Terminal crearTerminal(Terminal terminal) {
        if (terminal.getNombre() == null || terminal.getNombre().isBlank()) {
            throw new DatosNoValidosException("El nombre introducido no puede estar en blanco");
        }else if(terminalRepository.existsByNombreIgnoreCase(terminal.getNombre())) {
            throw new DatosNoValidosException("El nombre introducido ya existe");
        }else {
            return terminalRepository.save(terminal);
        }
    }
}
