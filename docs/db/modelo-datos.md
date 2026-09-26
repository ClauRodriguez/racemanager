# RaceManager — Esquema de Base de Datos

**Motor:** MySQL 8.x  
**Script:** [`database/schema.sql`](../../database/schema.sql)  
**Versión:** 2 (2da entrega) — importación sin duplicados y publicación auditada

## Cómo aplicarlo

```bash
mysql -u root -p < database/schema.sql
```

O desde MySQL Workbench: abrir y ejecutar `database/schema.sql`.

Para una base creada con la versión 1: `database/migraciones/002_importacion_y_publicacion.sql`.
Datos de ejemplo para desarrollo: `database/datos-desarrollo.sql`.

## Diagrama entidad-relación (conceptual)

```mermaid
erDiagram
    ROL ||--o{ USUARIO_ROL : tiene
    USUARIO ||--o{ USUARIO_ROL : tiene
    USUARIO ||--o{ LIGA : administra
    LIGA ||--o{ CATEGORIA : define
    LIGA ||--o{ EQUIPO : agrupa
    LIGA ||--o{ CARRERA : organiza
    LIGA ||--o{ VEHICULO : registra
    EQUIPO ||--o{ PILOTO : integra
    EQUIPO ||--o{ VEHICULO : usa
    CATEGORIA ||--o{ PILOTO : clasifica
    CIRCUITO ||--o{ CARRERA : sede
    CARRERA ||--o{ SESION : contiene
    CARRERA ||--o{ CARRERA_EQUIPO : participantes
    CARRERA ||--o{ CARRERA_PILOTO : participantes
    CARRERA ||--o{ IMPORTACION_CARRERA : carga
    SESION ||--o{ RESULTADO_SESION : genera
    PILOTO ||--o{ RESULTADO_SESION : obtiene
    RESULTADO_SESION ||--o{ VUELTA : desglosa

    USUARIO {
        bigint id PK
        varchar email
        varchar password_hash
        varchar nombre
    }
    ROL {
        bigint id PK
        varchar codigo
    }
    LIGA {
        bigint id PK
        varchar nombre
        varchar temporada
        bigint manager_id FK
    }
    EQUIPO {
        bigint id PK
        bigint liga_id FK
        varchar nombre
    }
    PILOTO {
        bigint id PK
        bigint equipo_id FK
        varchar nickname
    }
    VEHICULO {
        bigint id PK
        bigint liga_id FK
        varchar nombre_modelo
    }
    CIRCUITO {
        bigint id PK
        varchar nombre
        int longitud_metros
    }
    CARRERA {
        bigint id PK
        bigint liga_id FK
        bigint circuito_id FK
        enum estado
        datetime fecha_hora
    }
    SESION {
        bigint id PK
        bigint carrera_id FK
        enum tipo
    }
    RESULTADO_SESION {
        bigint id PK
        bigint sesion_id FK
        bigint piloto_id FK
        int mejor_vuelta_ms
    }
    VUELTA {
        bigint id PK
        bigint resultado_sesion_id FK
        int tiempo_total_ms
        int sector1_ms
        int sector2_ms
        int sector3_ms
    }
```

## Tablas principales

| Tabla | Propósito |
|---|---|
| `rol` / `usuario` / `usuario_rol` | Identidad y roles (ADMIN, MANAGER, EQUIPO, PILOTO) |
| `liga` / `categoria` | Competencias y categorías internas |
| `equipo` / `piloto` / `vehiculo` | Planteles y autos |
| `circuito` | Catálogo de pistas |
| `carrera` | Calendario y estados de publicación |
| `carrera_equipo` / `carrera_piloto` | Participantes |
| `sesion` / `resultado_sesion` / `vuelta` | Resultados y tiempos |
| `importacion_carrera` | Auditoría de archivos Assetto Corsa |

## Estados de carrera

`PROGRAMADA` → `CARGADA` → `PUBLICADA` (o `CANCELADA`)

- `CARGADA`: hay resultados importados y confirmados, visibles solo para el Manager de la liga.
- `PUBLICADA`: visible para equipos y pilotos. La base exige registrar `publicada_en` y
  `publicada_por` (restricción `ck_carrera_publicacion`), de modo que toda publicación queda auditada.

## Estados de importación

`PENDIENTE` → `PROCESADA` → `CONFIRMADA` | `RECHAZADA` (o `ERROR` si el archivo es inválido)

| Regla | Cómo se garantiza |
|---|---|
| El mismo archivo no se carga dos veces para la misma carrera | `UNIQUE (carrera_id, hash_sha256)` |
| Una carrera tiene como máximo una importación confirmada | Columna calculada `carrera_confirmada_id` + `UNIQUE` |
| Solo se publica lo revisado | La aplicación publica únicamente carreras con una importación `CONFIRMADA` |
| Nada queda a medias | La importación y la escritura de resultados se hacen en una sola transacción |

Para corregir resultados ya confirmados se rechaza la importación vigente y se carga una nueva
(la carrera vuelve a `CARGADA` si estaba publicada). El mismo archivo en **otra** carrera se
detecta en la aplicación comparando `hash_sha256` y se informa como advertencia.

## Decisiones pendientes del modelo

- **Piloto sin equipo o con cambio de equipo entre temporadas:** hoy `piloto.equipo_id` es obligatorio.
  Definir con el equipo antes de mapear la entidad JPA.
- **Correspondencia con archivos reales:** no ampliar `sesion`, `resultado_sesion` ni `vuelta`
  hasta analizar muestras reales de Assetto Corsa (ver `docs/importacion/relevamiento-archivos.md`).
