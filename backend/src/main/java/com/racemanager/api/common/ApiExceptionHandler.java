package com.racemanager.api.common;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.racemanager.api.auth.CredencialesInvalidasException;

/**
 * Respuestas de error uniformes y sin detalles internos.
 *
 * <p>Importante: no agregar un manejador genérico de {@code Exception}; interceptaría
 * {@code AccessDeniedException} y un acceso prohibido dejaría de responder 403.
 */
@RestControllerAdvice
public class ApiExceptionHandler {

	@ExceptionHandler(CredencialesInvalidasException.class)
	ResponseEntity<Map<String, String>> credencialesInvalidas(CredencialesInvalidasException ex) {
		return error(HttpStatus.UNAUTHORIZED, ex.getMessage());
	}

	@ExceptionHandler(RecursoNoEncontradoException.class)
	ResponseEntity<Map<String, String>> noEncontrado(RecursoNoEncontradoException ex) {
		return error(HttpStatus.NOT_FOUND, ex.getMessage());
	}

	@ExceptionHandler({ MethodArgumentNotValidException.class, HttpMessageNotReadableException.class })
	ResponseEntity<Map<String, String>> solicitudInvalida(Exception ex) {
		return error(HttpStatus.BAD_REQUEST, "Solicitud inválida");
	}

	private static ResponseEntity<Map<String, String>> error(HttpStatus estado, String mensaje) {
		return ResponseEntity.status(estado).body(Map.of("error", mensaje));
	}
}
