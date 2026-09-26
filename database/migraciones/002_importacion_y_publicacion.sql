-- =============================================================================
-- RaceManager — Migración 002: importaciones sin duplicados y publicación auditada
-- Aplicar SOLO sobre bases creadas con la versión 1 de schema.sql.
-- Una base nueva creada con el schema.sql actual ya incluye estos cambios.
-- Requiere MySQL 8.0.16+ (CHECK constraints).
-- =============================================================================

USE racemanager;

-- Carrera: quién y cuándo publicó
ALTER TABLE carrera
  ADD COLUMN publicada_en  DATETIME(3)     NULL AFTER notas,
  ADD COLUMN publicada_por BIGINT UNSIGNED NULL AFTER publicada_en,
  ADD CONSTRAINT fk_carrera_publicada_por FOREIGN KEY (publicada_por) REFERENCES usuario (id),
  ADD CONSTRAINT ck_carrera_publicacion CHECK (
    estado <> 'PUBLICADA' OR (publicada_en IS NOT NULL AND publicada_por IS NOT NULL));

-- Importación: huella del archivo, revisión y estados ampliados.
-- Si la tabla ya tuviera filas, completar hash_sha256 y tamano_bytes antes de
-- volverlos NOT NULL (en desarrollo la tabla está vacía).
ALTER TABLE importacion_carrera
  ADD COLUMN hash_sha256  CHAR(64)     NOT NULL AFTER ruta_almacenada,
  ADD COLUMN tamano_bytes INT UNSIGNED NOT NULL AFTER hash_sha256,
  MODIFY COLUMN estado ENUM('PENDIENTE', 'PROCESADA', 'CONFIRMADA', 'RECHAZADA', 'ERROR')
    NOT NULL DEFAULT 'PENDIENTE',
  ADD COLUMN revisado_por BIGINT UNSIGNED NULL AFTER procesado_en,
  ADD COLUMN revisado_en  DATETIME(3)     NULL AFTER revisado_por,
  ADD COLUMN carrera_confirmada_id BIGINT UNSIGNED
    AS (IF(estado = 'CONFIRMADA', carrera_id, NULL)) STORED,
  ADD UNIQUE KEY uk_importacion_carrera_hash (carrera_id, hash_sha256),
  ADD UNIQUE KEY uk_importacion_una_confirmada (carrera_confirmada_id),
  ADD CONSTRAINT fk_importacion_revisor FOREIGN KEY (revisado_por) REFERENCES usuario (id);
