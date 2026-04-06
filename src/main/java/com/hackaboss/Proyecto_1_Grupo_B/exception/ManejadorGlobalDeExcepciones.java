package com.hackaboss.Proyecto_1_Grupo_B.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class ManejadorGlobalDeExcepciones {
    @ExceptionHandler(DatosNoValidosException.class)
    public ResponseEntity<Map<String, String>> manejarDatosNoValidos(DatosNoValidosException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("Error: ", ex.getMessage()));
    }
}
