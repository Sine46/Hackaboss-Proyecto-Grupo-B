package com.hackaboss.Proyecto_1_Grupo_B.dto;

import com.hackaboss.Proyecto_1_Grupo_B.model.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class PedidoDto {
    private Long id;
    private List<Producto> productos;
    private double precioTotal;
    private LocalDateTime horaPedido;
}
