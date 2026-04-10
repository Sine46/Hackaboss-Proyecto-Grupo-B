package com.hackaboss.Proyecto_1_Grupo_B.exception;

public class CategoriaNoEncontradaException extends RuntimeException {
    public CategoriaNoEncontradaException(Long id) {
        super("Categoria con la id " + id + "no encontrada");
    }
}
