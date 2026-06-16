package com.hackaboss.Proyecto_1_Grupo_B.mapper;

import com.hackaboss.Proyecto_1_Grupo_B.dto.CategoriaDto;
import com.hackaboss.Proyecto_1_Grupo_B.model.Categoria;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class CategoriaMapper {
    private final ProductoMapper productoMapper;
    public CategoriaDto toDto(Categoria categoria) {
        CategoriaDto categoriaDto = new CategoriaDto();
        categoriaDto.setId(categoria.getId());
        categoriaDto.setNombre(categoria.getNombre());
        categoriaDto.setProductosDto(categoria.getProductos().stream().map(productoMapper::toDto).toList());
        return categoriaDto;
    }

    public List<CategoriaDto> toDtoList(List<Categoria> categorias){
        return categorias.stream()
                .map(this::toDto)
                .toList();
    }
}
