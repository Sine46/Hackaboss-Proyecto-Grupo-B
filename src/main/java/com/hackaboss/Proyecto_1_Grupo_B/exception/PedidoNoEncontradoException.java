package com.hackaboss.Proyecto_1_Grupo_B.exception;

public class PedidoNoEncontradoException extends RuntimeException {
    public PedidoNoEncontradoException(Long id) {
        super("Producto con id " + id + " no encontrado");
    }
}
