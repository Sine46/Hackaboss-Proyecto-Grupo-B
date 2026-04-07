package com.hackaboss.Proyecto_1_Grupo_B.exception;

public class TerminalNoEncontradoException extends RuntimeException {
    public TerminalNoEncontradoException(Long id) {
        super("Terminal con id " + id + " no encontrado");
    }
}
