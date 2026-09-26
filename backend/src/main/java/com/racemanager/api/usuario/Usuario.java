package com.racemanager.api.usuario;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

/**
 * Usuario de la plataforma (tabla {@code usuario}).
 * Solo se mapean los campos necesarios para autenticación; el resto se incorpora
 * a medida que los módulos lo requieran.
 */
@Entity
@Table(name = "usuario")
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true, length = 180)
	private String email;

	/** Hash BCrypt. Nunca se expone en respuestas de la API. */
	@Column(name = "password_hash", nullable = false)
	private String passwordHash;

	@Column(nullable = false, length = 120)
	private String nombre;

	@Column(nullable = false)
	private boolean activo = true;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
		name = "usuario_rol",
		joinColumns = @JoinColumn(name = "usuario_id"),
		inverseJoinColumns = @JoinColumn(name = "rol_id"))
	private Set<Rol> roles = new HashSet<>();

	protected Usuario() {
	}

	public Usuario(String email, String passwordHash, String nombre) {
		this.email = email;
		this.passwordHash = passwordHash;
		this.nombre = nombre;
	}

	public Long getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public String getNombre() {
		return nombre;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public Set<Rol> getRoles() {
		return roles;
	}

	public void agregarRol(Rol rol) {
		roles.add(rol);
	}

	/** Códigos de rol (ADMIN, MANAGER, ...) del usuario. */
	public Set<String> codigosDeRol() {
		return roles.stream().map(Rol::getCodigo).collect(Collectors.toUnmodifiableSet());
	}
}
