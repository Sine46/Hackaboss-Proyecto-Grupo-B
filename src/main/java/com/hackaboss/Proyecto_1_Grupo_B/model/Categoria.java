package com.hackaboss.Proyecto_1_Grupo_B.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
@Entity
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
   // private TipoCategoria tipoCategoria;
    @OneToMany(mappedBy = "id")
    private List<Producto> productos;



}
