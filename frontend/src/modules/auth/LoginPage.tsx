import { type FormEvent, useState } from 'react'
import axios from 'axios'
import { Link } from 'react-router-dom'
import { api } from '../../services/api'
import { borrarSesion, guardarSesion, obtenerSesion, type Sesion } from '../../services/sesion'

/** Inicio de sesión contra POST /api/auth/login. */
export function LoginPage() {
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')
  const [error, setError] = useState<string | null>(null)
  const [enviando, setEnviando] = useState(false)
  const [sesion, setSesion] = useState<Sesion | null>(() => obtenerSesion())

  async function iniciarSesion(evento: FormEvent<HTMLFormElement>) {
    evento.preventDefault()
    setError(null)
    setEnviando(true)
    try {
      const { data } = await api.post<Sesion>('/auth/login', { email, password })
      guardarSesion(data)
      setSesion(data)
      setPassword('')
    } catch (err) {
      if (axios.isAxiosError(err) && err.response?.status === 401) {
        setError('Email o contraseña incorrectos.')
      } else if (axios.isAxiosError(err) && err.response?.status === 400) {
        setError('Revisá el formato del email y la contraseña.')
      } else {
        setError('No se pudo conectar con el servidor. Intentá nuevamente.')
      }
    } finally {
      setEnviando(false)
    }
  }

  function cerrarSesion() {
    borrarSesion()
    setSesion(null)
  }

  if (sesion) {
    return (
      <main className="container py-5" style={{ maxWidth: 420 }}>
        <h1 className="h3 mb-3">Sesión iniciada</h1>
        <p className="mb-1">{sesion.usuario.nombre}</p>
        <p className="text-secondary small">
          {sesion.usuario.email} · {sesion.usuario.roles.join(', ')}
        </p>
        <button type="button" className="btn btn-outline-light" onClick={cerrarSesion}>
          Cerrar sesión
        </button>
      </main>
    )
  }

  return (
    <main className="container py-5" style={{ maxWidth: 420 }}>
      <h1 className="h3 mb-4">Iniciar sesión</h1>
      <form onSubmit={iniciarSesion} noValidate>
        <div className="mb-3">
          <label htmlFor="email" className="form-label">Email</label>
          <input
            id="email"
            type="email"
            className="form-control"
            autoComplete="username"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
          />
        </div>
        <div className="mb-3">
          <label htmlFor="password" className="form-label">Contraseña</label>
          <input
            id="password"
            type="password"
            className="form-control"
            autoComplete="current-password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />
        </div>
        {error && (
          <div className="alert alert-danger py-2" role="alert">
            {error}
          </div>
        )}
        <button type="submit" className="btn btn-danger w-100" disabled={enviando}>
          {enviando ? 'Ingresando…' : 'Ingresar'}
        </button>
      </form>
      <p className="mt-4 mb-0">
        <Link to="/">Volver al inicio</Link>
      </p>
    </main>
  )
}
