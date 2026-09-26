package com.racemanager.api.auth;

import java.util.Collection;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

/**
 * Identidad del usuario autenticado, reconstruida a partir de un JWT válido.
 * Es el "principal" disponible en los controladores mediante {@code @AuthenticationPrincipal}.
 */
public record UsuarioAutenticado(Long id, String email, Set<String> roles) {

	public UsuarioAutenticado {
		roles = roles == null ? Set.of() : Set.copyOf(roles);
	}

	public boolean tieneRol(String codigo) {
		return roles.contains(codigo);
	}

	public Collection<? extends GrantedAuthority> authorities() {
		return roles.stream().map(rol -> new SimpleGrantedAuthority("ROLE_" + rol)).toList();
	}
}
