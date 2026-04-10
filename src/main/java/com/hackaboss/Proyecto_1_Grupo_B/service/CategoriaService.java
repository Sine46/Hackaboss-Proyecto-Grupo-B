package com.hackaboss.Proyecto_1_Grupo_B.service;

import com.hackaboss.Proyecto_1_Grupo_B.dto.CategoriaDto;
import com.hackaboss.Proyecto_1_Grupo_B.exception.CategoriaNoEncontradaException;
import com.hackaboss.Proyecto_1_Grupo_B.exception.DatosNoValidosException;
import com.hackaboss.Proyecto_1_Grupo_B.mapper.ProductoMapper;
import com.hackaboss.Proyecto_1_Grupo_B.model.Categoria;
import com.hackaboss.Proyecto_1_Grupo_B.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {
    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ProductoMapper productoMapper;

    public List<CategoriaDto> listarCategorias(){

        return categoriaRepository.findAll().stream()
                .map(this::categoriaToDto)
                .toList();
    }

    public Categoria crearCategoria(Categoria categoria) {
        if (categoria.getNombre() == null || categoria.getNombre().isBlank()) {
            throw new DatosNoValidosException("El nombre introducido no puede estar en blanco");
        }else if (categoriaRepository.existsByNombreIgnoreCase(categoria.getNombre())) {
            throw new DatosNoValidosException("La categoria ya existe");
        }else {
            return categoriaRepository.save(categoria);
        }
    }

    public CategoriaDto categoriaToDto(Categoria categoria) {
        CategoriaDto categoriaDto = new CategoriaDto();
        categoriaDto.setId(categoria.getId());
        categoriaDto.setNombre(categoria.getNombre());
        categoriaDto.setProductosDto(categoria.getProductos().stream().map(productoMapper::toDto).toList());
        return categoriaDto;
    }

    public Categoria actualizarCategoria(Long categoriaId, Categoria categoria) {
        Categoria c = categoriaRepository.findById(categoriaId).orElseThrow(()-> new CategoriaNoEncontradaException(categoriaId));
        if(categoria.getNombre() == null || categoria.getNombre().isBlank()) {
            throw new DatosNoValidosException("El nombre introducido no puede estar en blanco");
        } else if(categoriaRepository.existsByNombreIgnoreCaseAndIdNot(categoria.getNombre(), c.getId())) {
            throw new DatosNoValidosException("Ya existe una categoria con ese nombre");
        }
        c.setNombre(categoria.getNombre());
        return categoriaRepository.save(c);
    }
}
