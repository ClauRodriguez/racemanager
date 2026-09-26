package com.racemanager.api.liga;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.racemanager.api.auth.UsuarioAutenticado;
import com.racemanager.api.usuario.Rol;

/**
 * Control de pertenencia a liga, usado desde {@code @PreAuthorize}:
 *
 * <pre>{@code @PreAuthorize("@ligaAccess.puedeGestionar(authentication, #ligaId)")}</pre>
 *
 * <p>Reglas: ADMIN puede gestionar cualquier liga; MANAGER solo aquellas cuyo
 * {@code manager_id} coincide con su id; el resto de los roles, ninguna.
 * Una liga inexistente se trata igual que una ajena (403) para no revelar qué ids existen.
 *
 * <p>Todo endpoint que reciba un {@code ligaId} (o un recurso que pertenezca a una liga:
 * carrera, equipo, piloto, importación) debe pasar por esta verificación.
 */
@Component("ligaAccess")
public class LigaAccessGuard {

	private final LigaRepository ligaRepository;

	public LigaAccessGuard(LigaRepository ligaRepository) {
		this.ligaRepository = ligaRepository;
	}

	public boolean puedeGestionar(Authentication autenticacion, Long ligaId) {
		if (autenticacion == null || ligaId == null
				|| !(autenticacion.getPrincipal() instanceof UsuarioAutenticado usuario)) {
			return false;
		}
		if (usuario.tieneRol(Rol.ADMIN)) {
			return true;
		}
		return usuario.tieneRol(Rol.MANAGER) && ligaRepository.existsByIdAndManagerId(ligaId, usuario.id());
	}
}
