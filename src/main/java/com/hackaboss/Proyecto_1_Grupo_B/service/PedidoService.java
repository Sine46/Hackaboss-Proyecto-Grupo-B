package com.hackaboss.Proyecto_1_Grupo_B.service;

import com.hackaboss.Proyecto_1_Grupo_B.dto.AgregarProductoDto;
import com.hackaboss.Proyecto_1_Grupo_B.dto.CrearPedidoDto;
import com.hackaboss.Proyecto_1_Grupo_B.dto.PedidoDto;
import com.hackaboss.Proyecto_1_Grupo_B.exception.DatosNoValidosException;
import com.hackaboss.Proyecto_1_Grupo_B.exception.PedidoNoEncontradoException;
import com.hackaboss.Proyecto_1_Grupo_B.exception.ProductoNoEncontradoException;
import com.hackaboss.Proyecto_1_Grupo_B.exception.TerminalNoEncontradoException;
import com.hackaboss.Proyecto_1_Grupo_B.mapper.PedidoMapper;
import com.hackaboss.Proyecto_1_Grupo_B.model.*;
import com.hackaboss.Proyecto_1_Grupo_B.repository.PedidoRepository;
import com.hackaboss.Proyecto_1_Grupo_B.repository.ProductoRepository;
import com.hackaboss.Proyecto_1_Grupo_B.repository.TerminalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PedidoService {
    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private TerminalRepository terminalRepository;
    @Autowired
    private PedidoMapper pedidoMapper;

    // crear pedido
    public PedidoDto crearPedido(CrearPedidoDto dto) {
        Terminal terminal = terminalRepository.findById(dto.getTerminalId())
                .orElseThrow(() -> new TerminalNoEncontradoException(dto.getTerminalId()));
        Pedido pedido = new Pedido();
        pedido.setTerminal(terminal);
        pedido.setEstado(Estado.CREADO);
        pedido.setHoraPedido(LocalDateTime.now());
        pedido.setPrecioTotal(0.0);
        Pedido guardado = pedidoRepository.save(pedido);
        return pedidoMapper.toDto(guardado);
    }

    // Añadir Producto a Pedido
    public PedidoDto agregarProducto(Long pedidoId, List<AgregarProductoDto> productosDto) {
        Map<Producto, Integer> productos = productosDto.stream()
                .collect(Collectors.toMap(
                        dto-> productoRepository.findById(dto.getProductoId())
                                .orElseThrow(()-> new ProductoNoEncontradoException(dto.getProductoId())),
                        AgregarProductoDto::getCantidad
                ));
        Pedido pedido = pedidoExiste(pedidoId);
        if (pedido.getEstado() != Estado.CREADO) {
            throw new DatosNoValidosException("No es posible modificar el pedido en este estado");
        }
        if (productos == null || productos.isEmpty()) throw new DatosNoValidosException("La lista de productos no puede estar vacía");

        productos.forEach((producto, cantidad) -> {
            if (producto.getStock()<cantidad) throw new DatosNoValidosException("No hay stock suficiente de " + producto.getNombre() + ". Cantidad maxima disponible: " + producto.getStock());
            producto.setStock(producto.getStock() - cantidad);
            PedidoProducto pedidoProducto = new PedidoProducto();
            pedidoProducto.setPedido(pedido);
            pedidoProducto.setProducto(producto);
            pedidoProducto.setCantidad(cantidad);
            pedidoProducto.setPrecioUnidad(producto.getPrecio());
            pedido.getPedidoProductos().add(pedidoProducto);
            producto.getPedidoProductos().add(pedidoProducto);
        });

        pedido.setPrecioTotal(calcularTotal(pedido));

        return pedidoMapper.toDto(pedidoRepository.save(pedido));
    }

    // Eliminar Producto a pedido
    public PedidoDto eliminarProducto(Long pedidoId, Long productoId) {
        Pedido pedido = pedidoExiste(pedidoId);
        if (pedido.getEstado() != Estado.CREADO) {
            throw new DatosNoValidosException("No es posible modificar el pedido en este estado");
        }
        boolean eliminado = pedido.getPedidoProductos()
                .removeIf(pp -> pp.getProducto().getId().equals(productoId));
        if (!eliminado) {
            throw new DatosNoValidosException("El producto no está en el pedido");
        }
        pedido.setPrecioTotal(calcularTotal(pedido));

        return pedidoMapper.toDto(pedidoRepository.save(pedido));
    }

    // cambiar estado de Pedido
    public PedidoDto cambiarEstado(Long pedidoId, Estado estado) {
        Pedido pedido = pedidoExiste(pedidoId);
        validarCambioEstado(pedido.getEstado());
        pedido.setEstado(estado);
        return pedidoMapper.toDto(pedidoRepository.save(pedido));
    }

    // Find by id/codigo
    public PedidoDto findById(Long pedidoId) {
        Pedido pedido = pedidoExiste(pedidoId);
        return pedidoMapper.toDto(pedido);
    }

    // listar pedidos
    public List<PedidoDto> getPedidos(Estado estado) {

        List<Pedido> pedidos = (estado == null)
                ? pedidoRepository.findAll()
                : pedidoRepository.findByEstado(estado);

        return pedidoMapper.toDtoList(pedidos);
    }

    // calcular Precio
    private double calcularTotal(Pedido pedido) {
        return pedido.getPedidoProductos().stream()
                .mapToDouble(p-> p.getPrecioUnidad() * p.getCantidad())
                .sum();

    }

    // validaciones
    // pedido existente
    private Pedido pedidoExiste(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new PedidoNoEncontradoException(id));
    }

    // estado
    private void validarCambioEstado(Estado estado) {
        if (estado == Estado.FINALIZADO) {
            throw new DatosNoValidosException("El pedido ya está finalizado");
        }


    }
}
