package com.hackaboss.Proyecto_1_Grupo_B.mapper;

import com.hackaboss.Proyecto_1_Grupo_B.dto.PedidoProductoDto;
import com.hackaboss.Proyecto_1_Grupo_B.model.PedidoProducto;
import com.hackaboss.Proyecto_1_Grupo_B.model.Producto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PedidoProductoMapper {
    public PedidoProductoDto toDto(PedidoProducto pedidoProducto) {
        Producto producto = pedidoProducto.getProducto();
        PedidoProductoDto dto = new PedidoProductoDto();
        dto.setId(producto.getId());
        dto.setNombre(producto.getNombre());
        dto.setPrecio(pedidoProducto.getPrecioUnidad());
        dto.setCategoriaNombre(producto.getCategoria().getNombre());
        dto.setCantidad(pedidoProducto.getCantidad());

        return dto;

    }

    public List<PedidoProductoDto> toDtoList(List<PedidoProducto> lista) {
        return lista.stream()
                .map(this::toDto)
                .toList();
    }
}
