package com.hackaboss.Proyecto_1_Grupo_B.service;

import com.hackaboss.Proyecto_1_Grupo_B.dto.CrearPedidoDto;
import com.hackaboss.Proyecto_1_Grupo_B.dto.PedidoDto;
import com.hackaboss.Proyecto_1_Grupo_B.dto.ProductoDto;
import com.hackaboss.Proyecto_1_Grupo_B.model.Estado;
import com.hackaboss.Proyecto_1_Grupo_B.model.Pedido;
import com.hackaboss.Proyecto_1_Grupo_B.model.Producto;
import com.hackaboss.Proyecto_1_Grupo_B.model.Terminal;
import com.hackaboss.Proyecto_1_Grupo_B.repository.PedidoRepository;
import com.hackaboss.Proyecto_1_Grupo_B.repository.ProductoRepository;
import com.hackaboss.Proyecto_1_Grupo_B.repository.TerminalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
@Service
public class PedidoService {
    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private TerminalRepository terminalRepository;

    // crear pedido
    public PedidoDto crearPedido(CrearPedidoDto dto) {
        Terminal terminal = terminalRepository.findById(dto.getTerminalId())
                .orElseThrow(() -> new RuntimeException("Terminal no encontrado"));
        Pedido pedido = new Pedido();
        pedido.setTerminal(terminal);
        pedido.setEstado(Estado.CREADO);
        pedido.setHoraPedido(LocalDateTime.now());
        pedido.setPrecioTotal(0.0);
        Pedido guardado = pedidoRepository.save(pedido);
        return toDto(guardado);
    }

    // Añadir Producto a Pedido
    public PedidoDto anyadirProducto(Long pedidoId, Long productoId) {
        Pedido pedido = pedidoRepository.findById(pedidoId).orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
        Producto producto = productoRepository.findById(productoId).orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        pedido.getProductos().add(producto);
        pedido.setPrecioTotal(calcularTotal(pedido));
        return toDto(pedidoRepository.save(pedido));
    }

    // Eliminar Producto a pedido
    public PedidoDto eliminarProducto(Long pedidoId, Long productoId) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
        boolean eliminado = pedido.getProductos()
                .removeIf(producto -> producto.getId().equals(productoId));
        if (!eliminado) {
            throw new RuntimeException("El producto no está en el pedido");
        }
        pedido.setPrecioTotal(calcularTotal(pedido));
        return toDto(pedidoRepository.save(pedido));
    }

    // cambiar estado de Pedido
    public PedidoDto cambiarEstado(Long pedidoId, Estado estado) {
        Pedido pedido = pedidoRepository.findById(pedidoId).orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
        pedido.setEstado(estado);
        return toDto(pedidoRepository.save(pedido));
    }

    // Find by id/codigo
    public PedidoDto findById(Long pedidoId) {
        Pedido pedido = pedidoRepository.findById(pedidoId).orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
        return toDto(pedido);
    }

    // listar pedidos
    public List<PedidoDto> findAll() {
        return pedidoRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    // listar por estado
    public List<PedidoDto> findByEstado(Estado estado) {
        List<Pedido> pedidos = pedidoRepository.findAll();
        return pedidos.stream()
                .filter(p -> p.getEstado() == estado)
                .sorted(Comparator.comparing(Pedido::getHoraPedido))
                .map(this::toDto)
                .toList();
    }

    // convertir a DTO
    private PedidoDto toDto(Pedido pedido) {
        PedidoDto dto = new PedidoDto();
        dto.setId(pedido.getId());
        dto.setPrecioTotal(pedido.getPrecioTotal());
        dto.setHoraPedido(pedido.getHoraPedido());
        dto.setProductos(
                pedido.getProductos()
                        .stream()
                        .map(producto -> {
                            ProductoDto productoDto = new ProductoDto();
                            productoDto.setId(producto.getId());
                            productoDto.setNombre(producto.getNombre());
                            productoDto.setPrecio(producto.getPrecio());
                            productoDto.setCategoria(producto.getCategoria());
                            return productoDto;
                        })
                        .toList()
        );
        return dto;
    }

    // calcular Precio
    private double calcularTotal(Pedido pedido) {
        return pedido.getProductos().stream()
                .mapToDouble(Producto::getPrecio)
                .sum();

    }


}
