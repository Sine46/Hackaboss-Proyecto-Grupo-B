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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class PedidoService {
    private final PedidoRepository pedidoRepository;
    private final ProductoRepository productoRepository;
    private final TerminalRepository terminalRepository;
    private final PedidoMapper pedidoMapper;

    // crear pedido
    public PedidoDto crearPedido(CrearPedidoDto dto) {
        Terminal terminal = terminalRepository.findById(dto.getTerminalId())
                .orElseThrow(() -> new TerminalNoEncontradoException(dto.getTerminalId()));
        Pedido pedido = new Pedido();

        pedido.setCodigo(generarCodigo());
        pedido.setTerminal(terminal);
        pedido.setEstado(Estado.CREADO);
        pedido.setHoraPedido(LocalDateTime.now());
        pedido.setPrecioTotal(0.0);

        Pedido guardado = pedidoRepository.save(pedido);
        return pedidoMapper.toDto(guardado);
    }

    // Añadir Producto a Pedido
    @Transactional
    public PedidoDto agregarProducto(Long pedidoId, List<AgregarProductoDto> productosDto) {

        if (productosDto == null || productosDto.isEmpty()) {
            throw new DatosNoValidosException("La lista de productos no puede estar vacía");
        }

        Pedido pedido = pedidoExiste(pedidoId);

        if (pedido.getEstado() != Estado.CREADO) {
            throw new DatosNoValidosException("No es posible modificar el pedido en este estado");
        }

        for (AgregarProductoDto dto : productosDto) {
            Producto producto = productoRepository.findById(dto.getProductoId())
                    .orElseThrow(() -> new ProductoNoEncontradoException(dto.getProductoId()));
            if (!Boolean.TRUE.equals(producto.getActivo())) {
                throw new DatosNoValidosException(
                        "El producto " + producto.getNombre() + " no está disponible"
                );
            }
            int cantidad = dto.getCantidad();

            if (producto.getStock() < cantidad) {
                throw new DatosNoValidosException(
                        "No hay stock suficiente de " + producto.getNombre() +
                                ". Cantidad máxima disponible: " + producto.getStock()
                );
            }

            PedidoProducto existente = pedido.getPedidoProductos().stream()
                    .filter(pp -> pp.getProducto().getId().equals(producto.getId()))
                    .findFirst()
                    .orElse(null);

            if (existente != null) {
                existente.setCantidad(existente.getCantidad() + cantidad);
                if(existente.getCantidad() <= 0) eliminarProducto(pedidoId, producto.getId());
            } else {
                PedidoProducto pedidoProducto = new PedidoProducto();
                pedidoProducto.setPedido(pedido);
                pedidoProducto.setProducto(producto);
                pedidoProducto.setCantidad(cantidad);
                pedidoProducto.setPrecioUnidad(producto.getPrecio());

                pedido.getPedidoProductos().add(pedidoProducto);
                producto.getPedidoProductos().add(pedidoProducto);
            }
            producto.setStock(producto.getStock() - cantidad);
        }

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
    public PedidoDto avanzarEstado(Long pedidoId) {
        Pedido pedido = pedidoExiste(pedidoId);
        switch (pedido.getEstado()) {
            case CREADO -> pedido.setEstado(Estado.FINALIZADO);
            case FINALIZADO -> pedido.setEstado(Estado.EN_PREPARACION);
            case EN_PREPARACION -> pedido.setEstado(Estado.LISTO);
            case LISTO -> pedido.setEstado(Estado.ENTREGADO);
            case ENTREGADO -> throw new DatosNoValidosException("El pedido ya ha sido entregado, no se puede modificar");
            default -> throw new DatosNoValidosException("Estado de pedido no válido");
        }
        return pedidoMapper.toDto(pedidoRepository.save(pedido));
    }

    // Find by id
    public PedidoDto findById(Long pedidoId) {

        Pedido pedido = pedidoExiste(pedidoId);

        return pedidoMapper.toDto(pedido);
    }

    // Find by código
    public PedidoDto findByCodigo(String codigo) {

        Pedido pedido = pedidoExiste(codigo);

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
                .mapToDouble(p -> p.getPrecioUnidad() * p.getCantidad())
                .sum();

    }
    // generar codigo de pedido
    private String generarCodigo() {
        return "PED-" + UUID.randomUUID().toString().replace("-","").substring(0, 5).toUpperCase();
    }
    // validaciones
    // pedido existente
    private Pedido pedidoExiste(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new PedidoNoEncontradoException(id));
    }
    private Pedido pedidoExiste(String codigo) {
        return pedidoRepository.findByCodigo(codigo)
                .orElseThrow(() -> new PedidoNoEncontradoException(codigo));
    }
}
