# RaceManager — Arquitectura y Módulos

**Actualizado:** 2da entrega — se agrega el estado de cada módulo.
Leyenda: ✅ implementado · 🟡 parcial · ⚪ pendiente · ➖ fuera del MVP

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

Seguridad: JWT en el encabezado Authorization; roles + pertenencia a la liga.
Ver docs/seguridad/seguridad.md
```

## Estructura del repositorio

```text
racemanager/
├── README.md
├── database/
│   ├── schema.sql              # Esquema MySQL canónico
│   ├── migraciones/            # Cambios para bases existentes
│   └── datos-desarrollo.sql    # Datos de prueba (solo desarrollo)
├── docs/
│   ├── propuesta/
│   ├── mvp/                    # Alcance y criterios de aceptación
│   ├── seguridad/
│   ├── importacion/
│   ├── db/
│   │   └── modelo-datos.md
│   └── arquitectura/
│       └── modulos.md          # Este documento
├── backend/                    # API Spring Boot
└── frontend/                   # App React + Vite
```

## Módulos Backend (`backend/`)

Paquete base: `com.racemanager.api`

| Módulo | Paquete | Responsabilidad | Estado |
|---|---|---|---|
| Auth | `auth` | Login, emisión y validación de JWT | ✅ login y `/me` · ⚪ alta de usuarios |
| Usuario | `usuario` | Usuarios y roles | 🟡 entidades y repositorios |
| Liga | `liga` | Ligas, categorías, **aislamiento por liga** (`@ligaAccess`) | 🟡 consulta y control de pertenencia |
| Equipo | `equipo` | Equipos por liga | ⚪ (precarga mínima en MVP) |
| Piloto | `piloto` | Pilotos e historial deportivo | ⚪ |
| Vehículo | `vehiculo` | Autos del simulador | ➖ |
| Circuito | `circuito` | Circuitos | ⚪ (catálogo precargado) |
| Carrera | `carrera` | Calendario y flujo de publicación | ⚪ (esquema listo) |
| Sesión | `sesion` | Sesiones, resultados y vueltas | ⚪ (a definir con archivos reales) |
| Importación | `importacion` | Parseo de archivos Assetto Corsa | ⚪ (esquema listo, bloqueado por muestras) |
| Estadística | `estadistica` | Rendimiento y comparaciones | ➖ salvo historial básico |
| Common / Config | `common`, `config` | Healthcheck, errores, seguridad | ✅ |

## Módulos Frontend (`frontend/`)

| Módulo | Carpeta | Responsabilidad | Estado |
|---|---|---|---|
| Landing | `src/modules/landing` | Página pública / marca | 🟡 |
| Auth | `src/modules/auth` | Login | ✅ conectado a la API |
| Ligas | `src/modules/ligas` | Gestión y consulta de ligas | ⚪ |
| Equipos | `src/modules/equipos` | Gestión de equipos | ⚪ |
| Pilotos | `src/modules/pilotos` | Gestión de pilotos | ⚪ |
| Carreras | `src/modules/carreras` | Calendario, carga y publicación | ⚪ |
| Estadísticas | `src/modules/estadisticas` | Historial del piloto (MVP) | ⚪ |
| Services | `src/services` | Cliente Axios con JWT y sesión | ✅ |

## Base de datos

- Script: [`database/schema.sql`](../../database/schema.sql)
- Documentación: [`docs/db/modelo-datos.md`](../db/modelo-datos.md)
