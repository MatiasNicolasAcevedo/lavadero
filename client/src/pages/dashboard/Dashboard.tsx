import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import useAuth from "../../services/Api";

interface Client {
  id: number;
  nombre: string;
  correoElectronico: string;
  telefono: string;
}

const API_URL = "https://lavaderoweb.onrender.com/v1/api";

const Dashboard: React.FC = () => {
  const { token, user } = useAuth();
  const [clients, setClients] = useState<Client[]>([]);
  const [searchTerm, setSearchTerm] = useState<string>("");
  const [modalOpen, setModalOpen] = useState<boolean>(false);
  const [newClient, setNewClient] = useState({
    nombre: "",
    correoElectronico: "",
    telefono: "",
  });
  // Estado para almacenar errores de validación
  const [errors, setErrors] = useState<{ telefono?: string }>({});

  // Obtiene los clientes incluyendo el token en el header
  const fetchClients = async () => {
    try {
      const response = await fetch(`${API_URL}/clientes`, {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
          "Authorization": `Bearer ${token}`,
        },
      });
      if (!response.ok) {
        throw new Error("Error al obtener los clientes");
      }
      const data = await response.json();
      setClients(data);
    } catch (error) {
      console.error("fetchClients:", error);
    }
  };

  useEffect(() => {
    if (token) {
      fetchClients();
    }
  }, [token]);

  const handleSearchChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setSearchTerm(e.target.value);
  };

  const filteredClients = clients.filter((client) =>
    client.nombre.toLowerCase().includes(searchTerm.toLowerCase())
  );

  const openModal = () => setModalOpen(true);
  const closeModal = () => {
    setModalOpen(false);
    setNewClient({ nombre: "", correoElectronico: "", telefono: "" });
    setErrors({});
  };

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setNewClient((prev) => ({ ...prev, [name]: value }));
    // Limpiar error del campo teléfono al escribir
    if (name === "telefono" && errors.telefono) {
      setErrors((prev) => ({ ...prev, telefono: undefined }));
    }
  };

  // Función para validar el campo teléfono
  const validateClient = () => {
    const newErrors: { telefono?: string } = {};
    if (!newClient.telefono.trim()) {
      newErrors.telefono = "El teléfono es requerido.";
    } else if (!/^\d{10,15}$/.test(newClient.telefono.trim())) {
      newErrors.telefono = "El teléfono debe contener entre 10 y 15 dígitos.";
    }
    return newErrors;
  };

  const handleAddClient = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    const errorsFound = validateClient();
    if (Object.keys(errorsFound).length > 0) {
      setErrors(errorsFound);
      return;
    }
    try {
      const response = await fetch(`${API_URL}/clientes`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          "Authorization": `Bearer ${token}`,
        },
        body: JSON.stringify(newClient),
      });
      if (!response.ok) {
        throw new Error("Error al agregar cliente");
      }
      await response.json();
      fetchClients(); // Actualiza la lista de clientes
      closeModal();
    } catch (error) {
      console.error("handleAddClient:", error);
    }
  };

  return (
    <div className="min-h-screen flex flex-col">
      <div className="flex-grow p-6">
        <h1 className="text-3xl font-bold text-[#007473] mb-4">
          Dashboard {user && <span className="text-lg md:text-3xl text-gray-600">- Hola, {user.fullName}</span>}
        </h1>
        <div className="flex justify-between items-center mb-4">
          <input
            type="text"
            placeholder="Buscar por nombre..."
            value={searchTerm}
            onChange={handleSearchChange}
            className="border border-gray-300 rounded px-4 py-2 w-1/3"
          />
          <button
            onClick={openModal}
            className="bg-[#007473] hover:bg-[#005f60] text-white px-4 py-2 rounded"
          >
            Agregar Cliente
          </button>
        </div>
        <div>
          {filteredClients.length === 0 ? (
            <p>No se encontraron clientes.</p>
          ) : (
            <ul className="space-y-2">
              {filteredClients.map((client) => (
                <li
                  key={client.id}
                  className="p-4 border rounded flex flex-col md:flex-row md:justify-between md:items-center"
                >
                  <div className="flex flex-col md:flex-row md:items-center md:space-x-4 flex-1">
                    <p className="font-bold md:w-1/3">{client.nombre}</p>
                    <p className="md:w-1/3">Email: {client.correoElectronico}</p>
                    <p className="md:w-1/3">Teléfono: {client.telefono}</p>
                  </div>
                  <div className="mt-2 md:mt-0">
                    <Link
                      to={`/clientes/${client.id}`}
                      className="bg-blue-500 hover:bg-blue-600 text-white px-4 py-2 rounded"
                    >
                      Ver Vehículos
                    </Link>
                  </div>
                </li>
              ))}
            </ul>
          )}
        </div>

        {/* Modal para agregar cliente */}
        {modalOpen && (
          <div className="fixed inset-0 flex items-center justify-center bg-black bg-opacity-50 z-50">
            <div className="bg-white rounded-lg p-6 w-96">
              <h2 className="text-2xl font-bold mb-4">Agregar Cliente</h2>
              <form onSubmit={handleAddClient} className="space-y-4">
                <div>
                  <label className="block text-sm font-medium text-gray-700">
                    Nombre
                  </label>
                  <input
                    type="text"
                    name="nombre"
                    value={newClient.nombre}
                    onChange={handleInputChange}
                    className="mt-1 block w-full border border-gray-300 rounded-md p-2"
                    required
                  />
                </div>
                <div>
                  <label className="block text-sm font-medium text-gray-700">
                    Correo Electrónico
                  </label>
                  <input
                    type="email"
                    name="correoElectronico"
                    value={newClient.correoElectronico}
                    onChange={handleInputChange}
                    className="mt-1 block w-full border border-gray-300 rounded-md p-2"
                    required
                  />
                </div>
                <div>
                  <label className="block text-sm font-medium text-gray-700">
                    Teléfono
                  </label>
                  <input
                    type="text"
                    name="telefono"
                    value={newClient.telefono}
                    onChange={handleInputChange}
                    className="mt-1 block w-full border border-gray-300 rounded-md p-2"
                    required
                  />
                  {errors.telefono && (
                    <p className="text-red-500 text-sm mt-1">{errors.telefono}</p>
                  )}
                </div>
                <div className="flex justify-end space-x-4">
                  <button
                    type="button"
                    onClick={closeModal}
                    className="bg-gray-300 hover:bg-gray-400 text-gray-800 px-4 py-2 rounded"
                  >
                    Cancelar
                  </button>
                  <button
                    type="submit"
                    className="bg-[#007473] hover:bg-[#005f60] text-white px-4 py-2 rounded"
                  >
                    Agregar
                  </button>
                </div>
              </form>
            </div>
          </div>
        )}
      </div>
    </div>
  );
};

export default Dashboard;
