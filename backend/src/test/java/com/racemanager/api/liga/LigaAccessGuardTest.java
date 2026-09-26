package com.racemanager.api.liga;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;

import com.racemanager.api.auth.UsuarioAutenticado;

class LigaAccessGuardTest {

	private static final Long LIGA_PROPIA = 10L;
	private static final Long LIGA_AJENA = 20L;

	private final LigaRepository repositorio = mock(LigaRepository.class);
	private final LigaAccessGuard guard = new LigaAccessGuard(repositorio);

	LigaAccessGuardTest() {
		when(repositorio.existsByIdAndManagerId(LIGA_PROPIA, 1L)).thenReturn(true);
		when(repositorio.existsByIdAndManagerId(LIGA_AJENA, 1L)).thenReturn(false);
	}

	@Test
	void managerGestionaSuPropiaLiga() {
		assertThat(guard.puedeGestionar(autenticado(1L, "MANAGER"), LIGA_PROPIA)).isTrue();
	}

	@Test
	void managerNoGestionaLigaAjena() {
		assertThat(guard.puedeGestionar(autenticado(1L, "MANAGER"), LIGA_AJENA)).isFalse();
	}

	@Test
	void adminGestionaCualquierLiga() {
		assertThat(guard.puedeGestionar(autenticado(99L, "ADMIN"), LIGA_AJENA)).isTrue();
	}

	@Test
	void pilotoOEquipoNoGestionanLigas() {
		assertThat(guard.puedeGestionar(autenticado(1L, "PILOTO"), LIGA_PROPIA)).isFalse();
		assertThat(guard.puedeGestionar(autenticado(1L, "EQUIPO"), LIGA_PROPIA)).isFalse();
	}

	@Test
	void anonimoOSinLigaNoGestiona() {
		Authentication anonimo = new AnonymousAuthenticationToken("clave", "anonimo",
			AuthorityUtils.createAuthorityList("ROLE_ANONYMOUS"));
		assertThat(guard.puedeGestionar(anonimo, LIGA_PROPIA)).isFalse();
		assertThat(guard.puedeGestionar(null, LIGA_PROPIA)).isFalse();
		assertThat(guard.puedeGestionar(autenticado(1L, "MANAGER"), null)).isFalse();
	}

	private static Authentication autenticado(Long id, String rol) {
		UsuarioAutenticado usuario = new UsuarioAutenticado(id, "u" + id + "@test", Set.of(rol));
		return UsernamePasswordAuthenticationToken.authenticated(usuario, null, usuario.authorities());
	}
}
