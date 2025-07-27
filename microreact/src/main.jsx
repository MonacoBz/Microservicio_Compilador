import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import { PaginaPrincipal } from './PaginaPrincipal'



createRoot(document.getElementById('root')).render(
  <StrictMode>
    <PaginaPrincipal></PaginaPrincipal>
  </StrictMode>,
)
