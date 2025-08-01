import React from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import { AuthProvider } from "./context/AuthContext";
import Login from './pages/Login'

function App() {

  return (
    <BrowserRouter>
      <AuthProvider>
        <Routes>
          {/* <Route path="/" element={<LandingPage />} /> */}
          <Route path="/login" element={<Login />} />
          {/* <Route path="/register" element={<RegisterPage />} />
          <Route path="/dashboard" element={<Dashboard />} /> */}
        </Routes>
      </AuthProvider>
    </BrowserRouter>
  )
}

export default App
