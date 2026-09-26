import { Link } from 'react-router-dom'

/** Placeholder del módulo Landing — se implementará según el diseño de Figma Make. */
export function LandingPage() {
  return (
    <main className="container py-5">
      <p className="text-uppercase small mb-2" style={{ color: 'var(--rm-red)' }}>
        RaceManager
      </p>
      <h1 className="display-4 fw-bold">
        <span style={{ color: 'var(--rm-red)' }}>RACE</span>MANAGER
      </h1>
      <p className="lead col-lg-7">
        Tu liga. Tus carreras. Tus datos. Módulo landing en preparación.
      </p>
      <div className="d-flex gap-3 mt-4">
        <Link className="btn btn-danger" to="/login">
          Explorar plataforma
        </Link>
        <Link className="btn btn-outline-light" to="/login">
          Iniciar sesión
        </Link>
      </div>
    </main>
  )
}
