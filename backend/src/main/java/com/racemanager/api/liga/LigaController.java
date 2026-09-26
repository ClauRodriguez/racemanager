package com.racemanager.api.liga;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.racemanager.api.auth.UsuarioAutenticado;
import com.racemanager.api.common.RecursoNoEncontradoException;
import com.racemanager.api.usuario.Rol;

/**
 * Consulta de ligas para la gestión (Manager / Admin).
 * El alta y la edición se agregan en la iteración 2 reutilizando las mismas reglas.
 */
@RestController
@RequestMapping("/api/ligas")
public class LigaController {

	private final LigaRepository ligaRepository;

	public LigaController(LigaRepository ligaRepository) {
		this.ligaRepository = ligaRepository;
	}

	/** ADMIN ve todas las ligas; MANAGER solo las propias. */
	@GetMapping
	@PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
	public List<LigaResponse> listar(@AuthenticationPrincipal UsuarioAutenticado usuario) {
		List<Liga> ligas = usuario.tieneRol(Rol.ADMIN)
			? ligaRepository.findAllByOrderByNombreAsc()
			: ligaRepository.findByManagerIdOrderByNombreAsc(usuario.id());
		return ligas.stream().map(LigaResponse::desde).toList();
	}

	@GetMapping("/{ligaId}")
	@PreAuthorize("@ligaAccess.puedeGestionar(authentication, #ligaId)")
	public LigaResponse obtener(@PathVariable("ligaId") @P("ligaId") Long ligaId) {
		return ligaRepository.findById(ligaId)
			.map(LigaResponse::desde)
			.orElseThrow(() -> new RecursoNoEncontradoException("Liga no encontrada"));
	}
}
