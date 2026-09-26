package com.racemanager.api.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequest(
		@NotBlank @Email @Size(max = 180) String email,
		@NotBlank @Size(max = 128) String password) {

	/** Evita que la contraseña aparezca en logs si se imprime el objeto. */
	@Override
	public String toString() {
		return "LoginRequest[email=" + email + ", password=***]";
	}
}
