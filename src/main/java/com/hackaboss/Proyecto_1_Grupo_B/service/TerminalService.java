package com.hackaboss.Proyecto_1_Grupo_B.service;

import com.hackaboss.Proyecto_1_Grupo_B.dto.TerminalDto;
import com.hackaboss.Proyecto_1_Grupo_B.exception.DatosNoValidosException;
import com.hackaboss.Proyecto_1_Grupo_B.exception.TerminalNoEncontradoException;
import com.hackaboss.Proyecto_1_Grupo_B.mapper.PedidoMapper;
import com.hackaboss.Proyecto_1_Grupo_B.mapper.TerminalMapper;
import com.hackaboss.Proyecto_1_Grupo_B.model.Terminal;
import com.hackaboss.Proyecto_1_Grupo_B.repository.TerminalRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class TerminalService {
    @Autowired
    private TerminalRepository terminalRepository;

    @Autowired
    private TerminalMapper terminalMapper;


    public List<TerminalDto> listarTerminales() {
        return terminalRepository.findAll().stream()
                .map(terminalMapper::toDto)
                .toList();
    }

    public TerminalDto crearTerminal(TerminalDto terminalDto) {
        Terminal terminal = new Terminal();
        if (terminal.getNombre() == null || terminal.getNombre().isBlank()) {
            throw new DatosNoValidosException("El nombre introducido no puede estar en blanco");
        } else if (terminalRepository.existsByNombreIgnoreCase(terminal.getNombre())) {
            throw new DatosNoValidosException("El nombre introducido ya existe");
        } else {
            terminal.setNombre(terminalDto.getNombre());
            terminalRepository.save(terminal);
            return terminalMapper.toDto(terminal);
        }
    }


    public TerminalDto actualizarTerminal(Long terminalId, Terminal terminal) {
        Terminal t = terminalRepository.findById(terminalId).orElseThrow(() -> new TerminalNoEncontradoException(terminalId));
        if (terminal.getNombre() == null || terminal.getNombre().isBlank()) {
            throw new DatosNoValidosException("El nombre introducido no puede estar en blanco");
        } else if (terminalRepository.existsByNombreIgnoreCaseAndIdNot(terminal.getNombre(), t.getId())) {
            throw new DatosNoValidosException("Ya existe una terminal con ese nombre");
        }
        t.setNombre(terminal.getNombre());
        terminalRepository.save(t);
        return terminalMapper.toDto(t);
    }

    public TerminalDto buscarTerminalMasUtilizada() {
        List<Terminal> terminales = terminalRepository.findAll();
        return terminales.stream()
                .max(Comparator.comparingInt(t-> t.getPedidos().size()))
                .map(terminalMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("No hay ninguna terminal registrada"));
    }
}
