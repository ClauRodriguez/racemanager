# RaceManager — Esquema de Base de Datos

**Motor:** MySQL 8.x  
**Script:** [`database/schema.sql`](../../database/schema.sql)  
**Entrega:** Arquitectura y Módulos (31/08 – 27/09)

## Cómo aplicarlo

```bash
mysql -u root -p < database/schema.sql
```

O desde MySQL Workbench: abrir y ejecutar `database/schema.sql`.

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
