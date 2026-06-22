package com.hackaboss.Proyecto_1_Grupo_B.mapper;

import com.hackaboss.Proyecto_1_Grupo_B.dto.PedidoDto;
import com.hackaboss.Proyecto_1_Grupo_B.dto.PedidoProductoDto;
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
        dto.setCodigo(pedido.getCodigo());
        dto.setProductos(
                pedido.getPedidoProductos()
                        .stream()
                        .map(pedidoProducto -> {
                            PedidoProductoDto productoDto = new PedidoProductoDto();

                            productoDto.setId(pedidoProducto.getProducto().getId());
                            productoDto.setNombre(pedidoProducto.getProducto().getNombre());
                            productoDto.setPrecio(pedidoProducto.getPrecioUnidad());
                            productoDto.setCategoriaNombre(
                                    pedidoProducto.getProducto().getCategoria().getNombre()
                            );
                            productoDto.setCantidad(pedidoProducto.getCantidad());

                            return productoDto;
                        })
                        .toList()
        );
        return dto;
    }

    public List<PedidoDto> toDtoList(List<Pedido> pedidos) {
        return pedidos.stream()
                .map(this::toDto)
                .toList();
    }
}
