# Cómo trabajamos en RaceManager

Somos tres personas trabajando en paralelo. Estas reglas evitan pisarnos el trabajo.

## Versiones acordadas

JDK **21** · Node **20+** · MySQL **8.0.16+**. Si alguien necesita otra versión, se discute antes en el grupo.

## Flujo con branches y Pull Requests

1. Nunca trabajar directamente sobre `main`.
2. Antes de empezar, actualizar `main` y crear una branch corta:
   ```bash
   git switch main
   git pull origin main
   git switch -c feature/<tema-breve>
   ```
   Prefijos: `feature/` (funcionalidad), `fix/` (corrección), `docs/` (documentación), `chore/` (configuración).
3. Commits chicos y descriptivos, en español: `feat(liga): alta de liga con control de pertenencia`.
   Tipos: `feat`, `fix`, `docs`, `test`, `refactor`, `build`, `chore`.
4. Subir la branch y abrir un Pull Request hacia `main` completando la plantilla:
   ```bash
   git push -u origin feature/<tema-breve>
   ```
5. Otro integrante revisa. Se mergea solo con el CI en verde y al menos una aprobación.
6. Después del merge, borrar la branch y volver al paso 2 para la siguiente tarea.

Si `main` avanzó mientras trabajabas: `git pull --rebase origin main` en tu branch y resolver conflictos antes de pedir revisión.

## Qué NO se sube

- `node_modules/`, `dist/`, `build/`, `.gradle/`, `*.tsbuildinfo` (ya están en `.gitignore`).
- Archivos `.env` o cualquier contraseña, token o `JWT_SECRET` real.
- Copias completas de otra carpeta del proyecto (en especial otro directorio `.git`).
- Archivos de resultados reales **sin anonimizar**.

Antes de hacer commit: `git status` y `git diff --staged` para ver exactamente qué se incluye.

## Base de datos

- `database/schema.sql` es la fuente de verdad del esquema.
- Todo cambio de esquema se hace también como script nuevo en `database/migraciones/` (numerado: `003_...sql`).

## Seguridad

Leer [`docs/seguridad/seguridad.md`](docs/seguridad/seguridad.md) antes de crear endpoints.
Regla corta: todo endpoint de gestión lleva `@PreAuthorize`, y si toca datos de una liga, `@ligaAccess`.

## Cuándo algo está terminado

Ver la definición de terminado en [`docs/mvp/criterios-aceptacion.md`](docs/mvp/criterios-aceptacion.md#definición-de-terminado-aplica-a-cada-tarea--pull-request).
