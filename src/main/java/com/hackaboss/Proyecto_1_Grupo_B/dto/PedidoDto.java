package com.hackaboss.Proyecto_1_Grupo_B.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class PedidoDto {
    private Long id;
    private List<ProductoDto> productos;
    private double precioTotal;
    private LocalDateTime horaPedido;
}
