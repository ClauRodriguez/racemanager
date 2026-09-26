package com.racemanager.api.auth;

import java.util.List;
import java.util.Map;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.racemanager.api.auth.dto.LoginRequest;
import com.racemanager.api.auth.dto.LoginResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private final AuthService authService;

	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	/** Público. Devuelve un JWT si las credenciales son válidas; 401 en caso contrario. */
	@PostMapping("/login")
	public LoginResponse login(@Valid @RequestBody LoginRequest solicitud) {
		return authService.login(solicitud);
	}

	/** Requiere token. Devuelve la identidad contenida en el JWT. */
	@GetMapping("/me")
	public Map<String, Object> me(@AuthenticationPrincipal UsuarioAutenticado usuario) {
		return Map.of(
			"id", usuario.id(),
			"email", usuario.email(),
			"roles", List.copyOf(usuario.roles()));
	}
}
