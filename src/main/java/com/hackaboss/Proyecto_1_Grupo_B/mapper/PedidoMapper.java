package com.hackaboss.Proyecto_1_Grupo_B.mapper;

import com.hackaboss.Proyecto_1_Grupo_B.dto.PedidoDto;
import com.hackaboss.Proyecto_1_Grupo_B.dto.ProductoDto;
import com.hackaboss.Proyecto_1_Grupo_B.model.Pedido;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PedidoMapper {

    public PedidoDto toDto(Pedido pedido) {
        PedidoDto dto = new PedidoDto();
        dto.setId(pedido.getId());
        dto.setPrecioTotal(pedido.getPrecioTotal());
        dto.setHoraPedido(pedido.getHoraPedido());
        dto.setProductos(
                pedido.getPedidoProductos()
                        .stream()
                        .map(pedidoProducto -> {
                            ProductoDto productoDto = new ProductoDto();
                            productoDto.setId(pedidoProducto.getId());
                            productoDto.setNombre(pedidoProducto.getProducto().getNombre());
                            productoDto.setPrecio(pedidoProducto.getProducto().getPrecio());
                            productoDto.setCategoriaNombre(pedidoProducto.getProducto().getCategoria().getNombre());
                            return productoDto;
                        })
                        .toList()
        );
        return dto;
    }

    public List<PedidoDto> toDtoList(List<Pedido> pedidos){
        return pedidos.stream()
                .map(this::toDto)
                .toList();
    }
}
