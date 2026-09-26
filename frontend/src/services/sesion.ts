/**
 * Manejo de la sesión en el navegador.
 *
 * El token se guarda en sessionStorage (se borra al cerrar la pestaña) y se descarta
 * al vencer. Los roles se usan solo para mostrar u ocultar opciones de la interfaz:
 * la autorización real siempre la decide el backend.
 */
export interface UsuarioSesion {
  id: number
  email: string
  nombre: string
  roles: string[]
}

export interface Sesion {
  token: string
  expiraEn: string
  usuario: UsuarioSesion
}

const CLAVE = 'racemanager.sesion'

export function guardarSesion(sesion: Sesion): void {
  try {
    sessionStorage.setItem(CLAVE, JSON.stringify(sesion))
  } catch {
    // almacenamiento no disponible (modo privado estricto): la sesión dura solo esta vista
  }
}

export function obtenerSesion(): Sesion | null {
  try {
    const guardada = sessionStorage.getItem(CLAVE)
    if (!guardada) return null
    const sesion = JSON.parse(guardada) as Sesion
    if (new Date(sesion.expiraEn).getTime() <= Date.now()) {
      borrarSesion()
      return null
    }
    return sesion
  } catch {
    return null
  }
}

export function obtenerToken(): string | null {
  return obtenerSesion()?.token ?? null
}

export function borrarSesion(): void {
  try {
    sessionStorage.removeItem(CLAVE)
  } catch {
    // sin almacenamiento disponible: no hay nada que borrar
  }
}
