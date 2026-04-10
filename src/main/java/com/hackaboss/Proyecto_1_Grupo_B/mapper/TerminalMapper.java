package com.hackaboss.Proyecto_1_Grupo_B.mapper;

import com.hackaboss.Proyecto_1_Grupo_B.dto.TerminalDto;
import com.hackaboss.Proyecto_1_Grupo_B.model.Terminal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TerminalMapper {
    @Autowired
    private PedidoMapper pedidoMapper;

    public TerminalDto toDto(Terminal terminal) {
        TerminalDto terminalDto = new TerminalDto();
        terminalDto.setId(terminal.getId());
        terminalDto.setNombre(terminal.getNombre());
        terminalDto.setPedidos(terminal.getPedidos().stream().map(pedidoMapper::toDto).toList());
        return terminalDto;
    }

    public List<TerminalDto> toDtoList(List<Terminal> terminales) {
        return terminales.stream()
                .map(this::toDto)
                .toList();
    }
}
