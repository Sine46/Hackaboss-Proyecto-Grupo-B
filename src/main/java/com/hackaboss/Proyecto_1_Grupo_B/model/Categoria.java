package com.hackaboss.Proyecto_1_Grupo_B.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
@Entity
public class Categoria {
    @Id
    private TipoCategoria tipoCategoria;
    @OneToMany(mappedBy = "producto")
    private List<Producto> productos;



}
