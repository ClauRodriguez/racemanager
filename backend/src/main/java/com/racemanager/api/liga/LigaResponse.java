package com.racemanager.api.liga;

public record LigaResponse(Long id, String nombre, String descripcion, String temporada, boolean activa) {

	static LigaResponse desde(Liga liga) {
		return new LigaResponse(liga.getId(), liga.getNombre(), liga.getDescripcion(), liga.getTemporada(),
			liga.isActiva());
	}
}
