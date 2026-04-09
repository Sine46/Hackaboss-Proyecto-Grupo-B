package com.hackaboss.Proyecto_1_Grupo_B.service;

import com.hackaboss.Proyecto_1_Grupo_B.dto.CrearProductoDto;
import com.hackaboss.Proyecto_1_Grupo_B.dto.ProductoDto;
import com.hackaboss.Proyecto_1_Grupo_B.exception.DatosNoValidosException;
import com.hackaboss.Proyecto_1_Grupo_B.exception.ProductoNoEncontradoException;
import com.hackaboss.Proyecto_1_Grupo_B.mapper.ProductoMapper;
import com.hackaboss.Proyecto_1_Grupo_B.model.Categoria;
import com.hackaboss.Proyecto_1_Grupo_B.model.Producto;
import com.hackaboss.Proyecto_1_Grupo_B.repository.CategoriaRepository;
import com.hackaboss.Proyecto_1_Grupo_B.repository.ProductoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepo;
    @Autowired
    private CategoriaRepository categoriaRepo;
    @Autowired
    private ProductoMapper productoMapper;



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

    public ProductoDto crearProducto(CrearProductoDto productoDto) {

        Producto producto = new Producto();
        if (productoDto.getNombre() == null || productoDto.getNombre().isBlank()) {
            throw new DatosNoValidosException("El producto necesita un nombre");
        } else if (productoRepo.existsByNombreIgnoreCase(productoDto.getNombre())) {
            throw new DatosNoValidosException("El producto ya existe");
        }
        if (productoDto.getPrecio() < 0) {
            throw new DatosNoValidosException("El precio debe ser mayor que 0");
        }

        producto.setCategoria(categoriaRepo.findById(productoDto.getCategoriaId())
                .orElseThrow(() -> new DatosNoValidosException("No se ha encontrado la categoria")));

        producto.setNombre(productoDto.getNombre());
        producto.setPrecio(productoDto.getPrecio());
        producto.setActivo(true);
        producto.setStock(0);
        productoRepo.save(producto);
        return productoMapper.toDto(producto);

    }


    public ProductoDto actualizarProducto(Long productoId, CrearProductoDto dto) {
        Producto p = productoRepo.findById(productoId)
                .orElseThrow(() -> new ProductoNoEncontradoException(productoId));

        Categoria c = categoriaRepo.findById(dto.getCategoriaId())
                .orElseThrow(()-> new DatosNoValidosException("Categoria no encontrada"));

        if (dto.getNombre() == null || dto.getNombre().isBlank()) {
            throw new DatosNoValidosException("El producto necesita un nombre");
        } else if (productoRepo.existsByNombreIgnoreCaseAndIdNot(dto.getNombre(),productoId)) {
            throw new DatosNoValidosException("El producto ya existe");
        }
        if (dto.getPrecio() == null || dto.getPrecio() < 0) {
            throw new DatosNoValidosException("El precio debe ser mayor que 0");
        }
        if (p.getStock() < 0) {                                        //Aqui no necesito hacer validacion null ya que tenemos "int" y es primitivo lo cual nunca es null
            throw new DatosNoValidosException("El stock debe ser positivo");
        }
        if (dto.getCategoriaId() == null) {
            throw new DatosNoValidosException("categoriaId es obligatorio");
        }

        p.setNombre(dto.getNombre());
        p.setPrecio(dto.getPrecio());
        p.setCategoria(c);
        p.setStock(p.getStock());
        p.setActivo(p.getActivo());

        Producto saved = productoRepo.save(p);
        return productoMapper.toDto(saved);


    }
            public void desactivar (Long id){
                productoRepo.findById(id).map(p -> {
                            p.setActivo(false);
                            return productoRepo.save(p);
                        })
                        .orElseThrow(() -> new EntityNotFoundException("No se ha podido encontrar el producto con el id " + id));
            }


        }
