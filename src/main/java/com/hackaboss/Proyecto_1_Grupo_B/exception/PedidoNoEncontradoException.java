package com.hackaboss.Proyecto_1_Grupo_B.exception;

public class PedidoNoEncontradoException extends RuntimeException {
    public PedidoNoEncontradoException(Long id) {
        super("Pedido con id " + id + " no encontrado");
    }
    public PedidoNoEncontradoException(String codigo) {
        super("Pedido con código " + codigo + " no encontrado");
    }
}
