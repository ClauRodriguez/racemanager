# RaceManager

Gestión integral de ligas y competencias de simracing (foco inicial: Assetto Corsa).

**Trabajo Final** — Diego Velardes · Claudio Rodriguez · Gastón Cejas
**Tutor:** Juan Ignacio Schiavonni

> **Estado (2da entrega):** estructura base, seguridad (login JWT, roles y aislamiento por liga) y
> esquema de base de datos listos. El flujo del MVP (carrera → importación → publicación → consulta)
> está en desarrollo. Ver [alcance del MVP](docs/mvp/alcance-mvp.md) y
> [criterios de aceptación](docs/mvp/criterios-aceptacion.md).

---

## Descripción

Plataforma web para centralizar ligas, pilotos, circuitos y carreras. El Manager/Organizador carga
los resultados oficiales a partir de los archivos de Assetto Corsa, los revisa y los publica;
equipos y pilotos consultan los resultados publicados.

## Arquitectura

```text
Frontend (React + Vite)  ──HTTP/REST + JWT──►  API (Spring Boot)  ──►  MySQL 8
                                                   ▲
                               Archivos de resultados de Assetto Corsa (carga del Manager)
```

Detalle: [`docs/arquitectura/modulos.md`](docs/arquitectura/modulos.md)

## Tecnologías y versiones

| Capa | Stack | Versión requerida |
|---|---|---|
| Backend | Java, Spring Boot 4, Spring Security, Spring Data JPA, JWT (jjwt), Gradle | **JDK 21** |
| Frontend | React 19, TypeScript, Vite, React Router, Axios, Bootstrap | **Node 20+** |
| Base de datos | MySQL | **8.0.16+** |

## Puesta en marcha

### 1. Base de datos

```bash
mysql -u root -p < database/schema.sql
mysql -u root -p < database/datos-desarrollo.sql   # opcional: usuarios y ligas de prueba
```

Crear un usuario de aplicación (no usar `root`): ver [`database/README.md`](database/README.md).

### 2. Backend

Definir las variables de entorno (referencia: [`backend/.env.example`](backend/.env.example)).
Spring Boot **no** lee archivos `.env`: configurarlas en IntelliJ (*Run Configuration → Environment
variables*) o en la terminal.

```powershell
# Windows PowerShell
$env:DB_USER="racemanager"; $env:DB_PASSWORD="<clave-local>"
$env:JWT_SECRET="<al menos 32 caracteres aleatorios>"
cd backend
.\gradlew.bat bootRun
```

```bash
# Linux / macOS
export DB_USER=racemanager DB_PASSWORD='<clave-local>' JWT_SECRET='<al menos 32 caracteres aleatorios>'
cd backend && ./gradlew bootRun
```

Si falta `JWT_SECRET` (o tiene menos de 32 caracteres) la API no arranca, a propósito.

Pruebas automáticas (usan H2 en memoria, no necesitan MySQL):

```bash
cd backend && ./gradlew test
```

### 3. Frontend

```bash
cd frontend
npm install
npm run dev
```

App: `http://localhost:5173` (el proxy de Vite redirige `/api` al backend en `:8080`).

## Endpoints disponibles

| Método | Ruta | Acceso | Descripción |
|---|---|---|---|
| GET | `/api/health` | Público | Estado del servicio |
| POST | `/api/auth/login` | Público | Devuelve un JWT |
| GET | `/api/auth/me` | Autenticado | Identidad del token |
| GET | `/api/ligas` | ADMIN, MANAGER | ADMIN: todas; MANAGER: solo las propias |
| GET | `/api/ligas/{ligaId}` | ADMIN o Manager de esa liga | Detalle de la liga |

Todo lo demás requiere autenticación o está denegado por defecto. Ver [`docs/seguridad/seguridad.md`](docs/seguridad/seguridad.md).

## Estructura del repositorio

```text
racemanager/
├── backend/           # API Spring Boot (paquetes por módulo de dominio)
├── frontend/          # App React + Vite (módulos de UI)
├── database/          # schema.sql, migraciones y datos de desarrollo
├── docs/              # propuesta, MVP, seguridad, arquitectura, modelo de datos
├── .github/           # CI y plantilla de Pull Request
└── CONTRIBUTING.md    # flujo de trabajo con branches y Pull Requests
```

## Documentación

| Documento | Ruta |
|---|---|
| Propuesta | [`docs/propuesta/propuesta-proyecto.md`](docs/propuesta/propuesta-proyecto.md) |
| Alcance del MVP | [`docs/mvp/alcance-mvp.md`](docs/mvp/alcance-mvp.md) |
| Criterios de aceptación y definición de terminado | [`docs/mvp/criterios-aceptacion.md`](docs/mvp/criterios-aceptacion.md) |
| Seguridad | [`docs/seguridad/seguridad.md`](docs/seguridad/seguridad.md) |
| Relevamiento de archivos Assetto Corsa | [`docs/importacion/relevamiento-archivos.md`](docs/importacion/relevamiento-archivos.md) |
| Arquitectura y módulos | [`docs/arquitectura/modulos.md`](docs/arquitectura/modulos.md) |
| Modelo de datos | [`docs/db/modelo-datos.md`](docs/db/modelo-datos.md) |
| Cómo contribuir | [`CONTRIBUTING.md`](CONTRIBUTING.md) |

## Repositorio

https://github.com/ClauRodriguez/racemanager
