package com.racemanager.api.auth.dto;

import java.time.Instant;
import java.util.List;

public record LoginResponse(String token, String tipo, Instant expiraEn, UsuarioResumen usuario) {

	public record UsuarioResumen(Long id, String email, String nombre, List<String> roles) {
	}
}
