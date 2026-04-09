package com.hackaboss.Proyecto_1_Grupo_B.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class TerminalDto {
    private Long id;
    private String nombre;
    private List<PedidoDto> pedidos;
}

