package com.hackaboss.Proyecto_1_Grupo_B.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
@Entity
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private Double precio;
    private int stock;
    @ManyToOne
    @JoinColumn(name="categoria")
    private Categoria categoria;
    @ManyToMany
    private List<Pedido> pedidos;
    private boolean disponible;

}
