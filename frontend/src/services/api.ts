import axios from 'axios'
import { borrarSesion, obtenerToken } from './sesion'

/** Cliente HTTP base hacia la API RaceManager. */
export const api = axios.create({
  baseURL: import.meta.env.VITE_API_URL ?? '/api',
  headers: { 'Content-Type': 'application/json' },
})

// Adjunta el JWT a cada solicitud si hay sesión iniciada.
api.interceptors.request.use((config) => {
  const token = obtenerToken()
  if (token) {
    config.headers.Authorization = `Bearer ${token}`
  }
  return config
})

// Si el backend responde 401 (token vencido o inválido), se descarta la sesión local.
api.interceptors.response.use(
  (respuesta) => respuesta,
  (error) => {
    if (axios.isAxiosError(error) && error.response?.status === 401) {
      borrarSesion()
    }
    return Promise.reject(error)
  },
)
