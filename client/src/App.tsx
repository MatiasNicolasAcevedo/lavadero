import { BrowserRouter as Router, Routes, Route, Navigate } from "react-router-dom";
import "./App.css";
import Navbar from "./components/NavBar";
import Footer from "./components/Footer";
import Index from "./pages/index/Index";
import { Login } from "./pages/login/Login";
import Register from "./pages/register/Register";
import { AuthProvider} from "./services/Api";
import useAuth from './services/Api';
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import Dashboard from "./pages/dashboard/Dashboard";
import ClientVehicles from "./components/ClientVehicles";
import Turnos from "./components/Turnos";

const ProtectedRoute: React.FC<{ element: React.ReactElement }> = ({ element }) => {
  const { isAuthenticated } = useAuth();
  return isAuthenticated ? element : <Navigate to="/login" />;
};

const UnauthenticatedRoute: React.FC<{ element: React.ReactElement }> = ({ element }) => {
  const { isAuthenticated } = useAuth();
  return isAuthenticated ? <Navigate to="/dashboard" /> : element;
};

function App() {
  return (
    <AuthProvider>
      <Router>
        <Navbar />
        <Routes>
          <Route path="/" element={<Index />} />
          <Route path="/login" element={<UnauthenticatedRoute element={<Login />} />} />
          <Route path="/register" element={<UnauthenticatedRoute element={<Register />} />} />
          <Route path="/dashboard" element={<ProtectedRoute element={<Dashboard />} />} />
          <Route path="/clientes/:clientId" element={<ProtectedRoute element={<ClientVehicles />} />} />
          <Route path="/turnos/:vehiculoId" element={<ProtectedRoute element={<Turnos />} />} />
        </Routes>
        <Footer />
        <ToastContainer
          position="bottom-right"
          autoClose={5000}
          hideProgressBar={false}
          newestOnTop={false}
          closeOnClick
          rtl={false}
          pauseOnFocusLoss
          draggable
          pauseOnHover
        />
      </Router>
    </AuthProvider>
  );
}

export default App;