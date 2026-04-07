package com.hackaboss.Proyecto_1_Grupo_B.repository;

import com.hackaboss.Proyecto_1_Grupo_B.model.Estado;
import com.hackaboss.Proyecto_1_Grupo_B.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByEstado(Estado estado);

}
