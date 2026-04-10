package com.hackaboss.Proyecto_1_Grupo_B.controller;

import com.hackaboss.Proyecto_1_Grupo_B.dto.CrearProductoDto;
import com.hackaboss.Proyecto_1_Grupo_B.dto.ProductoDto;
import com.hackaboss.Proyecto_1_Grupo_B.dto.StockDto;
import com.hackaboss.Proyecto_1_Grupo_B.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public ResponseEntity<List<ProductoDto>> listarProductos(@RequestParam(required = false) Boolean activo,
                                                             @RequestParam(required = false) Long categoriaId,
                                                             @RequestParam(required = false) String orden,
                                                             @RequestParam(required = false) Boolean desc) {

        return ResponseEntity.ok(productoService.listarProductos(activo, categoriaId, orden, desc));

    }

    @PostMapping
    public ResponseEntity<ProductoDto> crearProducto(@RequestBody CrearProductoDto productoDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productoService.crearProducto(productoDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoDto> actualizarProducto(@PathVariable("id") Long id, @RequestBody CrearProductoDto dto) {
        return ResponseEntity.ok(productoService.actualizarProducto(id, dto));
    }

    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<String> desactivarProducto(@PathVariable Long id) {
        productoService.desactivar(id);
        return ResponseEntity.ok("Producto desactivado");

    }

    @PatchMapping("/{id}/stock")
    public ResponseEntity<ProductoDto> modStock(@PathVariable Long id, @RequestBody StockDto stock) {
        return ResponseEntity.ok(productoService.modStock(id, stock.getStock()));
    }
}
