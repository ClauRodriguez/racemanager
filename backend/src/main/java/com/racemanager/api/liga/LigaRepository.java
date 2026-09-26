package com.racemanager.api.liga;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LigaRepository extends JpaRepository<Liga, Long> {

	List<Liga> findByManagerIdOrderByNombreAsc(Long managerId);

	List<Liga> findAllByOrderByNombreAsc();

	boolean existsByIdAndManagerId(Long id, Long managerId);
}
