package com.hackaboss.Proyecto_1_Grupo_B.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Data
@Entity
public class Pedido {
    private Long id;
    @ManyToMany
    private List<Producto> productos;
    @ManyToOne
    private Terminal terminal;
    private double precioTotal;
    private LocalDateTime horaPedido;
    private Estado estado;
}
