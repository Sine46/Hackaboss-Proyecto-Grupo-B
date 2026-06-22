package com.hackaboss.Proyecto_1_Grupo_B.mapper;

import com.hackaboss.Proyecto_1_Grupo_B.dto.ProductoDto;
import com.hackaboss.Proyecto_1_Grupo_B.model.Producto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductoMapper {
    public ProductoDto toDto(Producto producto) {
        ProductoDto dto = new ProductoDto();
        dto.setId(producto.getId());
        dto.setNombre(producto.getNombre());
        dto.setPrecio(producto.getPrecio());
        dto.setCategoriaNombre(producto.getCategoria().getNombre());
        dto.setStock(producto.getStock());
        return dto;
    }

    public List<ProductoDto> toDtoList(List<Producto> productos) {
        return productos.stream()
                .map(this::toDto)
                .toList();
    }
}
