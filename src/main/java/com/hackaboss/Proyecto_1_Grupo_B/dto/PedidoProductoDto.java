package com.hackaboss.Proyecto_1_Grupo_B.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PedidoProductoDto {
    private Long id;
    private String nombre;
    private double precio;
    private String categoriaNombre;
    private int cantidad;
}
