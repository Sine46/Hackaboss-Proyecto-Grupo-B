package com.hackaboss.Proyecto_1_Grupo_B.repository;

import com.hackaboss.Proyecto_1_Grupo_B.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CategoriaRepository extends JpaRepository<Categoria, TipoCategoria> {
}
