package com.hackaboss.Proyecto_1_Grupo_B.service;

import com.hackaboss.Proyecto_1_Grupo_B.dto.CrearProductoDto;
import com.hackaboss.Proyecto_1_Grupo_B.dto.ProductoDto;
import com.hackaboss.Proyecto_1_Grupo_B.dto.ProductoMasVendidoDto;
import com.hackaboss.Proyecto_1_Grupo_B.exception.DatosNoValidosException;
import com.hackaboss.Proyecto_1_Grupo_B.exception.ProductoNoEncontradoException;
import com.hackaboss.Proyecto_1_Grupo_B.mapper.ProductoMapper;
import com.hackaboss.Proyecto_1_Grupo_B.model.Categoria;
import com.hackaboss.Proyecto_1_Grupo_B.model.PedidoProducto;
import com.hackaboss.Proyecto_1_Grupo_B.model.Producto;
import com.hackaboss.Proyecto_1_Grupo_B.repository.CategoriaRepository;
import com.hackaboss.Proyecto_1_Grupo_B.repository.PedidoProductoRepository;
import com.hackaboss.Proyecto_1_Grupo_B.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Service
public class ProductoService {

    // Inyeccion de repos y mapper
    private final ProductoRepository productoRepo;
    private final CategoriaRepository categoriaRepo;
    private final ProductoMapper productoMapper;
    private final PedidoProductoRepository pedidoProductoRepository;

    // Metodo de listar productos
    public List<ProductoDto> listarProductos(Boolean activo, Long categoriaId, String orden, Boolean desc) {
        List<Producto> productos = productoRepo.findAll();

        Stream<Producto> stream = productos.stream();

        if (activo != null) {
            stream = stream.filter(p -> p.getActivo().equals(activo));
        }

        if (categoriaId != null) {
            stream = stream.filter(p -> p.getCategoria().getId().equals(categoriaId));
        }

        Comparator<Producto> comparator = Comparator.comparing(Producto::getNombre);

        switch (orden != null ? orden : "nombre") {
            case "precio":
                comparator = Comparator.comparing(Producto::getPrecio);
                break;
            case "categoria":
                comparator = Comparator.comparing(p -> p.getCategoria().getNombre());
                break;
            default:
                break;
        }

        if (desc != null && desc) {
            comparator = comparator.reversed();
        }

        return stream
                .sorted(comparator)
                .map(productoMapper::toDto)
                .toList();
    }

    // Metodo de crear un producto
    public ProductoDto crearProducto(CrearProductoDto productoDto) {

        Producto producto = new Producto();
        if (productoDto.getNombre() == null || productoDto.getNombre().isBlank()) {
            throw new DatosNoValidosException("El producto necesita un nombre");
        } else if (productoRepo.existsByNombreIgnoreCase(productoDto.getNombre())) {
            throw new DatosNoValidosException("El producto ya existe");
        }
        if (productoDto.getPrecio() < 0) {
            throw new DatosNoValidosException("El precio no puede ser menor que 0");
        }

        producto.setCategoria(categoriaRepo.findById(productoDto.getCategoriaId())
                .orElseThrow(() -> new DatosNoValidosException("No se ha encontrado la categoria")));

        producto.setNombre(productoDto.getNombre());
        producto.setPrecio(productoDto.getPrecio());
        producto.setActivo(true);
        if(productoDto.getStock()!=null && productoDto.getStock()>0){
            producto.setStock(productoDto.getStock());
        } else producto.setStock(0);
        productoRepo.save(producto);
        return productoMapper.toDto(producto);

    }

    // Metodo para actualizar un producto
    public ProductoDto actualizarProducto(Long productoId, CrearProductoDto dto) {
        Producto p = productoRepo.findById(productoId)
                .orElseThrow(() -> new ProductoNoEncontradoException(productoId));

        if (dto.getNombre() == null || dto.getNombre().isBlank()) {
            throw new DatosNoValidosException("El producto necesita un nombre");
        } else if (productoRepo.existsByNombreIgnoreCaseAndIdNot(dto.getNombre(), productoId)) {
            throw new DatosNoValidosException("El producto ya existe");
        }
        if (dto.getPrecio() == null || dto.getPrecio() < 0) {
            throw new DatosNoValidosException("El precio debe ser mayor que 0");
        }
        if (dto.getStock() == null || dto.getStock() < 0) {
            throw new DatosNoValidosException("El stock debe ser positivo");
        }
        if (dto.getCategoriaId() == null) {
            throw new DatosNoValidosException("categoriaId es obligatorio");
        }
        if(dto.getEstado() != null) p.setActivo(dto.getEstado());

        Categoria c = categoriaRepo.findById(dto.getCategoriaId())
                .orElseThrow(() -> new DatosNoValidosException("Categoria no encontrada"));


        p.setNombre(dto.getNombre());
        p.setPrecio(dto.getPrecio());
        p.setCategoria(c);
        p.setStock(dto.getStock());

        productoRepo.save(p);
        return productoMapper.toDto(p);
    }
    // Metodo de desactivar un Producto
    public void desactivar(Long id) {
        productoRepo.findById(id).map(p -> {
                    p.setActivo(false);
                    return productoRepo.save(p);
                })
                .orElseThrow(() -> new ProductoNoEncontradoException(id));
    }

    // Modificar Stock
    public ProductoDto modStock(Long productoId, Integer stock) {
        if (stock == null || stock < 0) throw new DatosNoValidosException("El stock debe ser mayor que 0");
        Producto producto = productoRepo.findById(productoId)
                .orElseThrow(() -> new ProductoNoEncontradoException(productoId));

        producto.setStock(stock);
        productoRepo.save(producto);
        return productoMapper.toDto(producto);
    }

    //Buscar Producto Mas Vendido
    public List<ProductoMasVendidoDto> getProductosMasVendidos() {

        Map<Producto, Integer> conteo = pedidoProductoRepository.findAll().stream()
                .collect(Collectors.groupingBy(
                        PedidoProducto::getProducto,
                        Collectors.summingInt(PedidoProducto::getCantidad)
                ));

        return conteo.entrySet().stream()
                .sorted(Map.Entry.<Producto,Integer>comparingByValue().reversed())
                .map(e -> new ProductoMasVendidoDto(
                        productoMapper.toDto(e.getKey()),
                        e.getValue()
                ))
                .collect(Collectors.toList());
    }

}
