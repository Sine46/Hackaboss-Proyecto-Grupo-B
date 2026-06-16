package com.hackaboss.Proyecto_1_Grupo_B.service;

import com.hackaboss.Proyecto_1_Grupo_B.dto.CategoriaDto;
import com.hackaboss.Proyecto_1_Grupo_B.exception.CategoriaNoEncontradaException;
import com.hackaboss.Proyecto_1_Grupo_B.exception.DatosNoValidosException;
import com.hackaboss.Proyecto_1_Grupo_B.mapper.CategoriaMapper;
import com.hackaboss.Proyecto_1_Grupo_B.model.Categoria;
import com.hackaboss.Proyecto_1_Grupo_B.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoriaService {
    private final CategoriaRepository categoriaRepository;

    private final CategoriaMapper categoriaMapper;

    public List<CategoriaDto> listarCategorias() {

        return categoriaRepository.findAll().stream()
                .map(categoriaMapper::toDto)
                .toList();
    }

    public CategoriaDto crearCategoria(CategoriaDto categoriaDto) {
         Categoria categoria = new Categoria();
         categoria.setNombre(categoriaDto.getNombre());
        if (categoriaDto.getNombre() == null || categoriaDto.getNombre().isBlank()) {
            throw new DatosNoValidosException("El nombre introducido no puede estar en blanco");
        } else if (categoriaRepository.existsByNombreIgnoreCase(categoria.getNombre())) {
            throw new DatosNoValidosException("La categoria ya existe");
        } else {
            categoriaRepository.save(categoria);
            return categoriaMapper.toDto(categoria);
        }
    }

    public CategoriaDto actualizarCategoria(Long categoriaId, Categoria categoria) {
        Categoria c = categoriaRepository.findById(categoriaId).orElseThrow(()-> new CategoriaNoEncontradaException(categoriaId));
        if(categoria.getNombre() == null || categoria.getNombre().isBlank()) {
            throw new DatosNoValidosException("El nombre introducido no puede estar en blanco");
        } else if(categoriaRepository.existsByNombreIgnoreCaseAndIdNot(categoria.getNombre(), c.getId())) {
            throw new DatosNoValidosException("Ya existe una categoria con ese nombre");
        }
        c.setNombre(categoria.getNombre());
        categoriaRepository.save(c);
        return categoriaMapper.toDto(c);
    }
}
