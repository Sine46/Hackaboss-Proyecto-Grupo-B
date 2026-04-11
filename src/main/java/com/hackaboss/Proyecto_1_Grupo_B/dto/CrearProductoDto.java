package com.hackaboss.Proyecto_1_Grupo_B.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CrearProductoDto {

    private String nombre;
    private Double precio;
    private Long categoriaId;
    private Integer stock;
    private Boolean estado;
}
