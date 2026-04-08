package com.hackaboss.Proyecto_1_Grupo_B.repository;

import com.hackaboss.Proyecto_1_Grupo_B.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    boolean existsByNombreIgnoreCase(String nombre);
}
