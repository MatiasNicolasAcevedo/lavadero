import Terms from './Terms';
import { FaFacebookF, FaInstagram, FaTwitter, FaYoutube, FaLinkedinIn } from 'react-icons/fa';
import logoFooter from '../assets/logo-footer.svg';

const Footer = () => {
  return (
    <footer className="bg-[#007473] py-5 w-full">
      <div className="container mx-auto px-4 flex flex-col md:flex-row justify-between items-center">
        {/* Logo a la izquierda */}
        <div className="mb-4 md:mb-0">
          <img src={logoFooter} alt="Logo Lavadero" className="h-12" />
        </div>
        {/* Centro: Términos y copyright */}
        <div className="text-center mb-4 md:mb-0">
          <p className="text-white font-semibold">
            2025 &copy; Lavadero de Autos. Matias Acevedo. Todos los derechos reservados.
          </p>
          <div className="mt-2">
            <Terms />
          </div>
        </div>
        {/* Iconos de redes sociales a la derecha */}
        <div className="flex space-x-4">
          <a 
            href="https://facebook.com/munidigital" 
            target="_blank" 
            rel="noopener noreferrer" 
            className="text-white hover:text-gray-300"
          >
            <FaFacebookF size={20} />
          </a>
          <a 
            href="https://instagram.com/munidigital" 
            target="_blank" 
            rel="noopener noreferrer" 
            className="text-white hover:text-gray-300"
          >
            <FaInstagram size={20} />
          </a>
          <a 
            href="https://twitter.com/MuniDigital_" 
            target="_blank" 
            rel="noopener noreferrer" 
            className="text-white hover:text-gray-300"
          >
            <FaTwitter size={20} />
          </a>
          <a 
            href="https://www.youtube.com/MuniDigital_" 
            target="_blank" 
            rel="noopener noreferrer" 
            className="text-white hover:text-gray-300"
          >
            <FaYoutube size={20} />
          </a>
          <a 
            href="https://linkedin.com/company/munidigital" 
            target="_blank" 
            rel="noopener noreferrer" 
            className="text-white hover:text-gray-300"
          >
            <FaLinkedinIn size={20} />
          </a>
        </div>
      </div>
    </footer>
  );
};

export default Footer;
