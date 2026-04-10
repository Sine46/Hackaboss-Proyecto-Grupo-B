package com.hackaboss.Proyecto_1_Grupo_B.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Data
@Entity
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PedidoProducto> pedidoProductos = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "terminal_id")
    private Terminal terminal;

    private double precioTotal;
    private LocalDateTime horaPedido;

    @Enumerated(EnumType.STRING)
    private Estado estado;
}
