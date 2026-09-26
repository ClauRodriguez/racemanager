package com.racemanager.api.liga;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Liga o competencia (tabla {@code liga}). {@code managerId} identifica al único
 * Manager que puede administrarla: es la base del aislamiento entre ligas.
 */
@Entity
@Table(name = "liga")
public class Liga {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 150)
	private String nombre;

	@Column(columnDefinition = "TEXT")
	private String descripcion;

	@Column(length = 40)
	private String temporada;

	@Column(name = "manager_id", nullable = false)
	private Long managerId;

	@Column(nullable = false)
	private boolean activa = true;

	protected Liga() {
	}

	public Liga(String nombre, String temporada, Long managerId) {
		this.nombre = nombre;
		this.temporada = temporada;
		this.managerId = managerId;
	}

	public Long getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public String getTemporada() {
		return temporada;
	}

	public Long getManagerId() {
		return managerId;
	}

	public boolean isActiva() {
		return activa;
	}
}
