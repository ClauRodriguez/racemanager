## Qué cambia

<!-- Resumen breve. Si cierra un issue: "Cierra #N" -->

## Tipo de cambio

- [ ] Documentación
- [ ] Backend
- [ ] Frontend
- [ ] Base de datos (incluye migración en `database/migraciones/`)
- [ ] Configuración / CI

## Criterios de aceptación relacionados

<!-- Ej.: CA-02 — ver docs/mvp/criterios-aceptacion.md -->

## Cómo probarlo

1.
2.

## Checklist (definición de terminado)

- [ ] El CI pasa (backend, esquema MySQL y frontend).
- [ ] Agregué pruebas; si hay endpoints nuevos: anónimo (401), rol incorrecto (403), Manager de otra liga (403) y caso válido.
- [ ] Todo endpoint de gestión usa `@PreAuthorize` y, si corresponde, `@ligaAccess`.
- [ ] No incluye secretos, `.env`, `node_modules`, `build`, `dist` ni archivos generados.
- [ ] Actualicé la documentación afectada (README, esquema, endpoints, criterios).
- [ ] Pedí revisión a otro integrante.
