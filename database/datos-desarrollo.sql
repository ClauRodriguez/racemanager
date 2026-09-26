-- =============================================================================
-- RaceManager — Datos de DESARROLLO (nunca ejecutar en producción)
-- Crea usuarios y ligas de ejemplo para probar login, roles y aislamiento entre ligas.
--
-- Contraseña de TODOS los usuarios: RaceManager-Dev-2026!
-- (hash BCrypt; cambiarla o borrar estos usuarios antes de cualquier despliegue)
-- =============================================================================

USE racemanager;

SET @hash = '$2a$10$OotflFGFw6lx4gH3FZ53yO8J10Nx9lvzHb.9Ifn2juPENNRl7CXfq';

INSERT INTO usuario (email, password_hash, nombre, nickname) VALUES
  ('admin@racemanager.dev',    @hash, 'Admin Desarrollo', 'admin'),
  ('manager.a@racemanager.dev', @hash, 'Manager Liga A',  'managerA'),
  ('manager.b@racemanager.dev', @hash, 'Manager Liga B',  'managerB'),
  ('piloto@racemanager.dev',    @hash, 'Piloto Demo',     'piloto1');

INSERT INTO usuario_rol (usuario_id, rol_id)
SELECT u.id, r.id FROM usuario u JOIN rol r ON
  (u.email = 'admin@racemanager.dev'     AND r.codigo = 'ADMIN')   OR
  (u.email = 'manager.a@racemanager.dev' AND r.codigo = 'MANAGER') OR
  (u.email = 'manager.b@racemanager.dev' AND r.codigo = 'MANAGER') OR
  (u.email = 'piloto@racemanager.dev'    AND r.codigo = 'PILOTO');

INSERT INTO liga (nombre, temporada, manager_id)
SELECT 'Liga A (demo)', '2026', id FROM usuario WHERE email = 'manager.a@racemanager.dev';
INSERT INTO liga (nombre, temporada, manager_id)
SELECT 'Liga B (demo)', '2026', id FROM usuario WHERE email = 'manager.b@racemanager.dev';
