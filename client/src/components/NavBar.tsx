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

  const isAuthenticated = user !== null || localStorage.getItem("token") !== null;

  return (
    <nav className="font-sans flex flex-col sm:flex-row items-center justify-between px-2 py-4 md:px-4 lg:px-12 bg-white shadow w-full">
      {/* Header: logo y botón de hamburguesa */}
      <div className="flex items-center justify-between w-full sm:w-auto">
        <Link to="/">
          <img src={logo} alt="Logo Lavadero" className="lg:h-20 h-8 md:h-16" />
        </Link>
        <button
          className="sm:hidden text-3xl text-[#007473] hover:text-[#005f60]"
          onClick={toggleMenu}
        >
          &#9776;
        </button>
      </div>

      {/* Menú colapsable */}
      <div
        className={`sm:flex items-center space-x-4 lg:space-x-10 ${isMenuOpen ? "block" : "hidden"
          } mt-4 sm:mt-0 w-full sm:w-auto`}
      >
        <Link
          to="/#home"
          className="flex items-center text-md md:text-xl no-underline text-[#007473] hover:text-[#005f60] font-bold transition duration-150 ease-in-out"
        >
          Inicio
        </Link>
        <a
          href="/#faq"
          className="flex items-center text-md md:text-xl no-underline text-[#007473] hover:text-[#005f60] font-bold transition duration-150 ease-in-out"
        >
          Servicios
        </a>
        <a
          href="/#about"
          className="flex items-center text-md md:text-xl no-underline text-[#007473] hover:text-[#005f60] font-bold transition duration-150 ease-in-out"
        >
          Nosotros
        </a>
        <a
          href="https://github.com/MatiasNicolasAcevedo/lavadero"
          className="flex items-center text-md md:text-xl no-underline text-[#007473] hover:text-[#005f60] font-bold transition duration-150 ease-in-out"
          target="_blank"
          rel="noopener noreferrer"
        >
          Repositorio
        </a>
        {!isAuthenticated && (
          <Link
            to="/login"
            className="flex items-center mt-2 sm:mt-0 ml-4 w-32 hover:bg-[#cc5f20] bg-[#e26824] text-white text-md md:text-xl py-2 px-6 text-center font-bold rounded-lg transition duration-150 ease-in-out"
          >
            Ingresar
          </Link>
        )}
        {isAuthenticated && (
          <button
            onClick={logOut}
            className="flex items-center mt-2 sm:mt-0 md:ml-4 w-38 hover:bg-[#cc5f20] bg-[#e26824] text-white text-md md:text-xl py-2 px-1 md:px-6 text-center font-bold rounded-lg transition duration-150 ease-in-out"
          >
            Cerrar sesión
          </button>
        )}
      </div>
    </nav>

  );
}
