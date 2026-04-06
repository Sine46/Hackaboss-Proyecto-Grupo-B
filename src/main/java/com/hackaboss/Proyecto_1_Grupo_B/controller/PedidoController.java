package com.hackaboss.Proyecto_1_Grupo_B.controller;

import com.hackaboss.Proyecto_1_Grupo_B.dto.CrearPedidoDto;
import com.hackaboss.Proyecto_1_Grupo_B.dto.PedidoDto;
import com.hackaboss.Proyecto_1_Grupo_B.model.Estado;
import com.hackaboss.Proyecto_1_Grupo_B.service.PedidoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {
    @Autowired
    private PedidoService pedidoService;
    // Crear pedido
    @PostMapping
    public ResponseEntity<PedidoDto> crearPedido(@RequestBody CrearPedidoDto dto) {
        PedidoDto pedido = pedidoService.crearPedido(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(pedido);
    }

    // añadir producto a pedido
    @PostMapping("/{pedidoId}/productos")
    public ResponseEntity<PedidoDto> añadirProducto(@PathVariable Long pedidoId, @RequestParam Long productoId) {
        PedidoDto pedido = pedidoService.anyadirProducto(pedidoId, productoId);
        return ResponseEntity.ok(pedido);
    }

    // eliminar producto a pedido
    @DeleteMapping("/{pedidoId}/productos/{productoId}")
    public ResponseEntity<PedidoDto> eliminarProducto(@PathVariable Long pedidoId, @PathVariable Long productoId) {
        PedidoDto pedido = pedidoService.eliminarProducto(pedidoId, productoId);
        return ResponseEntity.ok(pedido);
    }

    // Cambiar estado del pedido
    @PatchMapping("/{pedidoId}/estado")
    public ResponseEntity<PedidoDto> cambiarEstado(@PathVariable Long pedidoId, @RequestParam Estado estado) {
        PedidoDto pedido = pedidoService.cambiarEstado(pedidoId,estado);
        return ResponseEntity.ok(pedido);
    }

    // Find by ID/Codigo
    @GetMapping("/{id}")
    public ResponseEntity<PedidoDto> findById(@PathVariable Long id) {
        PedidoDto pedido = pedidoService.findById(id);
        return ResponseEntity.ok(pedido);
    }

    // listar todos
    @GetMapping
    public ResponseEntity<List<PedidoDto>> findAll() {
        List<PedidoDto> pedidoDtos = pedidoService.listAll();
        return  ResponseEntity.ok(pedidoDtos);
    }

    // listar por estado
    @GetMapping
    public ResponseEntity<List<PedidoDto>> findByEstado(@RequestParam Estado estado) {
        List<PedidoDto> pedidoDtos = pedidoService.findByEstado(estado);
        return  ResponseEntity.ok(pedidoDtos);
    }
}
