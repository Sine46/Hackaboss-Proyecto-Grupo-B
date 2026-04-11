package com.hackaboss.Proyecto_1_Grupo_B.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoMasVendidoDto {
    private ProductoDto producto;
    private int totalVendido;
}
