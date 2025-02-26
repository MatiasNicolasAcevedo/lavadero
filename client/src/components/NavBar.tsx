import { useState } from "react";
import { Link } from "react-router-dom";
import useAuth from "../services/Api";
import logo from "../assets/logo-header.png";

export default function NavBar() {
  const { logout, user } = useAuth();
  const [isMenuOpen, setIsMenuOpen] = useState<boolean>(false);

  const toggleMenu = () => {
    setIsMenuOpen(!isMenuOpen);
  };

  const logOut = () => {
    logout();
  };

  // Se verifica si el usuario está autenticado comprobando tanto el user en el contexto como el token en localStorage
  const isAuthenticated = user !== null || localStorage.getItem("token") !== null;

  return (
    <nav className="font-sans flex flex-col md:h-28 sm:flex-row sm:justify-between py-4 px-6 lg:px-24 bg-white shadow sm:items-center w-full">
      <div className="flex justify-between items-center">
        <Link to="/">
          <img src={logo} alt="Logo Lavadero" className="lg:h-20 h-16" />
        </Link>
        <button className="sm:hidden text-3xl text-white" onClick={toggleMenu}>
          &#9776;
        </button>
      </div>
      <div
        className={`sm:flex flex-col sm:flex-row sm:items-center space-y-2 sm:space-y-0 space-x-3 lg:space-x-10 ${
          isMenuOpen ? "block" : "hidden"
        } mt-4 sm:mt-0`}
      >
        <Link
          to="/#home"
          className="text-base md:text-xl no-underline text-[#007473] hover:text-[#005f60] font-bold transition duration-150 ease-in-out"
        >
          Inicio
        </Link>
        <a
          href="/#faq"
          className="text-base md:text-xl no-underline text-[#007473] hover:text-[#005f60] font-bold transition duration-150 ease-in-out"
        >
          Servicios
        </a>
        <a
          href="/#about"
          className="text-base md:text-xl no-underline text-[#007473] hover:text-[#005f60] font-bold transition duration-150 ease-in-out"
        >
          Nosotros
        </a>
        <a
          href="https://github.com/MatiasNicolasAcevedo/lavadero"
          className="text-base md:text-xl no-underline text-[#007473] hover:text-[#005f60] font-bold transition duration-150 ease-in-out"
          target="_blank"
          rel="noopener noreferrer"
        >
          Respositorio
        </a>
        {!isAuthenticated ? (
          <Link
            to="/login"
            className="mt-2 sm:mt-0 ml-4 w-32 hover:bg-[#cc5f20] bg-[#e26824] text-white text-base md:text-xl py-2 px-6 text-center font-bold rounded-lg transition duration-150 ease-in-out"
          >
            Ingresar
          </Link>
        ) : (
          <Link
            to="/login"
            onClick={logOut}
            className="mt-2 sm:mt-0 ml-4 w-32 hover:bg-[#cc5f20] bg-[#e26824] text-white text-base md:text-xl py-2 px-6 text-center font-bold rounded-lg transition duration-150 ease-in-out"
          >
            Cerrar sesión
          </Link>
        )}
      </div>
    </nav>
  );
}
