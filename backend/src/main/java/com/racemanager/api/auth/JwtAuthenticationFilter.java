package com.racemanager.api.auth;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Lee el encabezado {@code Authorization: Bearer <token>} y, si el token es válido,
 * registra al usuario en el contexto de seguridad. Un token inválido o vencido se
 * ignora: la solicitud continúa como anónima y las reglas de acceso responden 401.
 *
 * <p>No se registra como {@code @Component} para que Spring Boot no lo agregue
 * además como filtro de servlet; se incorpora solo en {@code SecurityConfig}.
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private static final String PREFIJO = "Bearer ";

	private final JwtService jwtService;

	public JwtAuthenticationFilter(JwtService jwtService) {
		this.jwtService = jwtService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		String encabezado = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (encabezado != null && encabezado.startsWith(PREFIJO)) {
			String token = encabezado.substring(PREFIJO.length()).trim();
			try {
				UsuarioAutenticado usuario = jwtService.validar(token);
				UsernamePasswordAuthenticationToken autenticacion =
					UsernamePasswordAuthenticationToken.authenticated(usuario, null, usuario.authorities());
				autenticacion.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(autenticacion);
			}
			catch (JwtException | IllegalArgumentException ex) {
				SecurityContextHolder.clearContext();
			}
		}
		chain.doFilter(request, response);
	}
}
