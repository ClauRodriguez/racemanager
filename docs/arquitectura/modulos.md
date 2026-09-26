# RaceManager — Arquitectura y Módulos

**Período:** 31/08 – 27/09  
**Entregable:** esquema de base de datos + listado de módulos en el repositorio.

## Arquitectura general

```text
┌─────────────────────┐
│      FRONTEND       │  React + Vite
│  (módulos UI/rol)   │
└──────────┬──────────┘
           │ HTTP / REST (/api)
           ▼
┌─────────────────────┐
│       BACKEND       │  Spring Boot + Security + JPA
│   (módulos dominio) │
└──────────┬──────────┘
           ▼
┌─────────────────────┐
│      DATABASE       │  MySQL 8
│  database/schema.sql│
└─────────────────────┘

Assetto Corsa (archivos oficiales)
        │
        │ carga manual del Manager
        ▼
  Módulo Importación → API RaceManager
```

## Estructura del repositorio

```text
racemanager/
├── README.md
├── database/
│   └── schema.sql              # Esquema MySQL canónico
├── docs/
│   ├── propuesta/
│   ├── db/
│   │   └── modelo-datos.md
│   └── arquitectura/
│       └── modulos.md          # Este documento
├── backend/                    # API Spring Boot
└── frontend/                   # App React + Vite
```

## Módulos Backend (`backend/`)

Paquete base: `com.racemanager.api`

| Módulo | Paquete | Responsabilidad |
|---|---|---|
| Auth | `auth` | Login/registro, JWT, autorización |
| Usuario | `usuario` | Usuarios y asignación de roles |
| Liga | `liga` | Ligas, categorías, aislamiento por organización |
| Equipo | `equipo` | Equipos por liga |
| Piloto | `piloto` | Pilotos e historial deportivo |
| Vehículo | `vehiculo` | Autos del simulador |
| Circuito | `circuito` | Circuitos |
| Carrera | `carrera` | Calendario y flujo de publicación |
| Sesión | `sesion` | Sesiones, resultados y vueltas |
| Importación | `importacion` | Parseo de archivos Assetto Corsa |
| Estadística | `estadistica` | Rendimiento y comparaciones |
| Common / Config | `common`, `config` | Healthcheck, seguridad base |

## Módulos Frontend (`frontend/`)

| Módulo | Carpeta | Responsabilidad |
|---|---|---|
| Landing | `src/modules/landing` | Página pública / marca |
| Auth | `src/modules/auth` | Login / registro |
| Ligas | `src/modules/ligas` | Gestión y consulta de ligas |
| Equipos | `src/modules/equipos` | Gestión de equipos |
| Pilotos | `src/modules/pilotos` | Gestión de pilotos |
| Carreras | `src/modules/carreras` | Calendario, carga y publicación |
| Estadísticas | `src/modules/estadisticas` | Dashboard y análisis |
| Services | `src/services` | Cliente HTTP (Axios) hacia la API |

## Base de datos

- Script: [`database/schema.sql`](../../database/schema.sql)
- Documentación: [`docs/db/modelo-datos.md`](../db/modelo-datos.md)
