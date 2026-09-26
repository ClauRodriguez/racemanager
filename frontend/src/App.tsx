import { Route, Routes } from 'react-router-dom'
import { LandingPage } from './modules/landing/LandingPage'
import { LoginPage } from './modules/auth/LoginPage'

export default function App() {
  return (
    <Routes>
      <Route path="/" element={<LandingPage />} />
      <Route path="/login" element={<LoginPage />} />
    </Routes>
  )
}
