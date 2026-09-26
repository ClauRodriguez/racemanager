import { Link } from 'react-router-dom'

/** Placeholder del módulo Auth. */
export function LoginPage() {
  return (
    <main className="container py-5" style={{ maxWidth: 420 }}>
      <h1 className="h3 mb-3">Iniciar sesión</h1>
      <p className="text-secondary">Módulo de autenticación — pendiente de conectar con la API.</p>
      <Link to="/">Volver al inicio</Link>
    </main>
  )
}
