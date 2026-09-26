package com.racemanager.api.usuario;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/** Rol de la plataforma: ADMIN, MANAGER, EQUIPO o PILOTO (tabla {@code rol}). */
@Entity
@Table(name = "rol")
public class Rol {

	public static final String ADMIN = "ADMIN";
	public static final String MANAGER = "MANAGER";
	public static final String EQUIPO = "EQUIPO";
	public static final String PILOTO = "PILOTO";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true, length = 32)
	private String codigo;

	@Column(nullable = false, length = 80)
	private String nombre;

	protected Rol() {
	}

	public Rol(String codigo, String nombre) {
		this.codigo = codigo;
		this.nombre = nombre;
	}

	public Long getId() {
		return id;
	}

	public String getCodigo() {
		return codigo;
	}

	public String getNombre() {
		return nombre;
	}
}
