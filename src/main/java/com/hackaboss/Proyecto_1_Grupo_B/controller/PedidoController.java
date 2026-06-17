package com.hackaboss.Proyecto_1_Grupo_B.controller;

import com.hackaboss.Proyecto_1_Grupo_B.dto.AgregarProductoDto;
import com.hackaboss.Proyecto_1_Grupo_B.dto.CrearPedidoDto;
import com.hackaboss.Proyecto_1_Grupo_B.dto.PedidoDto;
import com.hackaboss.Proyecto_1_Grupo_B.model.Estado;
import com.hackaboss.Proyecto_1_Grupo_B.service.PedidoService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/pedidos")
@CrossOrigin(origins = "http://localhost:5173")
public class PedidoController {
    private final PedidoService pedidoService;

    // Crear pedido
    @PostMapping
    public ResponseEntity<PedidoDto> crearPedido(@RequestBody CrearPedidoDto dto) {
        PedidoDto pedido = pedidoService.crearPedido(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(pedido);
    }

    // añadir producto a pedido
    @PostMapping("/{pedidoId}/productos")
    public ResponseEntity<PedidoDto> agregarProducto(@PathVariable Long pedidoId, @RequestBody List<AgregarProductoDto> productos) {
        return ResponseEntity.ok(pedidoService.agregarProducto(pedidoId, productos));
    }

    // eliminar producto a pedido
    @DeleteMapping("/{pedidoId}/productos/{productoId}")
    public ResponseEntity<PedidoDto> eliminarProducto(@PathVariable Long pedidoId, @PathVariable Long productoId) {
        return ResponseEntity.ok(pedidoService.eliminarProducto(pedidoId, productoId));
    }

    // Cambiar estado del pedido
    @PatchMapping("/{pedidoId}/estado")
    public ResponseEntity<PedidoDto> avanzarEstado(@PathVariable Long pedidoId) {
        return ResponseEntity.ok(pedidoService.avanzarEstado(pedidoId)
        );
    }

    // Find by ID/Codigo
    @GetMapping("/{id}")
    public ResponseEntity<PedidoDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(pedidoService.findById(id));
    }
    @GetMapping("/codigo/{codigo}")
    public PedidoDto findByCodigo(@PathVariable String codigo) {
        return pedidoService.findByCodigo(codigo);
    }

    // listar pedidos
    @GetMapping
    public ResponseEntity<List<PedidoDto>> getPedidos(@RequestParam(required = false) Estado estado) {
        return ResponseEntity.ok(pedidoService.getPedidos(estado));
    }

}
