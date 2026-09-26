# Relevamiento de archivos de Assetto Corsa

Plantilla para documentar las muestras reales **antes** de diseñar el importador (iteración 0).
Mientras esta tabla esté vacía, el formato de importación no está definido.

## Reglas para las muestras

- Pedir al menos **dos** archivos a una liga real: uno completo y válido, otro con variaciones
  (abandonos, desconexiones, vueltas inválidas, pilotos nuevos).
- **Anonimizar** antes de subirlos al repositorio: reemplazar nombres, Steam IDs y cualquier dato personal.
- Guardarlos en `backend/src/test/resources/importacion/` para usarlos como casos de prueba.
- No subir archivos de más de 2 MB.

## Muestras recibidas

| # | Origen (liga / servidor) | Fecha | Nombre de archivo | Formato | Tamaño | Codificación | Sesiones incluidas | Anonimizado |
|---|---|---|---|---|---|---|---|---|
| 1 | | | | | | | | ☐ |
| 2 | | | | | | | | ☐ |

## Campos observados

| Campo en el archivo | Ejemplo (anonimizado) | ¿Siempre presente? | Destino en el esquema | Observaciones |
|---|---|---|---|---|
| | | | `resultado_sesion.posicion` | |
| | | | `resultado_sesion.mejor_vuelta_ms` | |
| | | | `vuelta.tiempo_total_ms` | |
| | | | `vuelta.sector1_ms` … | |
| | | | identificación del piloto | ¿nombre, GUID, nickname? |

## Casos especiales detectados

- [ ] Pilotos que abandonan / no terminan
- [ ] Vueltas inválidas (cortes de pista)
- [ ] Penalizaciones
- [ ] Pilotos no registrados en la liga
- [ ] Varias sesiones en un mismo archivo
- [ ] Unidades de tiempo (ms, s, texto)

## Conclusión

Formato elegido para el MVP: _(completar)_
Campos que se importarán: _(completar)_
Campos que quedan fuera: _(completar)_
