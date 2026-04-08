package com.hackaboss.Proyecto_1_Grupo_B.dto;

import com.hackaboss.Proyecto_1_Grupo_B.model.Categoria;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class ProductoDto {
    private Long id;
    private String nombre;
    private Double precio;
    private String categoriaNombre;

}
