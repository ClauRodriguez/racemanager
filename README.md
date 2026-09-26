# RaceManager

Gestión integral de ligas y competencias de simracing (foco inicial: Assetto Corsa).

**Trabajo Final** — Diego Velardes · Claudio Rodriguez · Gastón Cejas  
**Tutor:** Juan Ignacio Schiavonni

---

## Descripción

Plataforma web para centralizar ligas, equipos, pilotos, vehículos, circuitos y carreras. El Manager/Organizador carga los datos oficiales; equipos y pilotos consultan resultados y estadísticas publicados.

## Arquitectura

```text
Frontend (React + Vite)  →  API REST (Spring Boot)  →  MySQL
                                      ↑
                    Importación de archivos Assetto Corsa
```

Detalle: [`docs/arquitectura/modulos.md`](docs/arquitectura/modulos.md)

## Tecnologías

| Capa | Stack |
|---|---|
| Backend | Java, Spring Boot, Spring Security, Spring Data JPA, JWT, Gradle |
| Frontend | React, Vite, React Router, Axios, Bootstrap |
| Base de datos | MySQL 8 |
| Integración | Archivos de resultados Assetto Corsa, REST, JSON |

## Estructura del repositorio

```text
racemanager/
├── database/          # Esquema SQL (schema.sql)
├── docs/              # Propuesta, arquitectura y modelo de datos
├── backend/           # API Spring Boot (módulos de dominio)
└── frontend/          # App React + Vite (módulos de UI)
```

## Módulos en el repositorio

### Backend (`backend/src/main/java/com/racemanager/api/`)

- `auth` — autenticación JWT
- `usuario` — usuarios y roles
- `liga` — ligas y categorías
- `equipo` — equipos
- `piloto` — pilotos
- `vehiculo` — vehículos
- `circuito` — circuitos
- `carrera` — carreras y publicación
- `sesion` — sesiones, resultados y vueltas
- `importacion` — carga Assetto Corsa
- `estadistica` — análisis de rendimiento

### Frontend (`frontend/src/modules/`)

- `landing` · `auth` · `ligas` · `equipos` · `pilotos` · `carreras` · `estadisticas`

### Base de datos

- [`database/schema.sql`](database/schema.sql) — esquema MySQL
- [`docs/db/modelo-datos.md`](docs/db/modelo-datos.md) — diagrama y descripción

## Puesta en marcha rápida

### 1. Base de datos

```bash
mysql -u root -p < database/schema.sql
```

Ajustar usuario/clave en `backend/src/main/resources/application.properties`.

### 2. Backend

```bash
cd backend
./gradlew bootRun
```

Healthcheck: `GET http://localhost:8080/api/health`

### 3. Frontend

```bash
cd frontend
npm install
npm run dev
```

App: `http://localhost:5173`

## Documentación

| Documento | Ruta |
|---|---|
| Propuesta | [`docs/propuesta/propuesta-proyecto.md`](docs/propuesta/propuesta-proyecto.md) |
| Arquitectura y módulos | [`docs/arquitectura/modulos.md`](docs/arquitectura/modulos.md) |
| Modelo de datos | [`docs/db/modelo-datos.md`](docs/db/modelo-datos.md) |

## Estado

Entrega **Arquitectura y Módulos** (31/08 – 27/09): esquema de BD y estructura de módulos subidos al repositorio. Implementación de negocio pendiente (Etapa 2+).

## Repositorio

https://github.com/ClauRodriguez/racemanager
