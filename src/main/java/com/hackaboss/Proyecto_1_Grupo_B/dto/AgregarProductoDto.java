package com.hackaboss.Proyecto_1_Grupo_B.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AgregarProductoDto {
    private Long productoId;
    private Integer cantidad;
}
