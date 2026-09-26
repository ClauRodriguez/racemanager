package com.racemanager.api.auth;

/** Credenciales rechazadas. El mensaje es deliberadamente genérico. */
public class CredencialesInvalidasException extends RuntimeException {

	public CredencialesInvalidasException() {
		super("Credenciales inválidas");
	}
}
