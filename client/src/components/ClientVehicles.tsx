import React, { useEffect, useState } from "react";
import { useParams, Link } from "react-router-dom";
import useAuth from "../services/Api";

interface PropiedadesAdicionales {
  [key: string]: string | number | boolean | undefined;
}

interface Vehiculo {
  id: number;
  modelo: string;
  matricula: string;
  tipo: string;
  clienteId: number;
  propiedadesAdicionales: PropiedadesAdicionales;
}

const API_URL = "https://lavaderoweb.onrender.com/v1/api";

const ClientVehicles: React.FC = () => {
  const { clientId } = useParams<{ clientId: string }>();
  const { token } = useAuth();
  const [vehicles, setVehicles] = useState<Vehiculo[]>([]);
  const [modalOpen, setModalOpen] = useState<boolean>(false);
  const [newVehicle, setNewVehicle] = useState({
    modelo: "",
    matricula: "",
    tipo: "",
  });
  // Estado para los campos dinámicos (clave y valor)
  const [dynamicProps, setDynamicProps] = useState<Array<{ key: string, value: string }>>([]);

  const fetchVehicles = async () => {
    try {
      const response = await fetch(`${API_URL}/vehiculos/cliente/${clientId}`, {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
          "Authorization": `Bearer ${token}`,
        },
      });
      if (!response.ok) {
        throw new Error("Error al obtener los vehículos");
      }
      const data = await response.json();
      setVehicles(data);
    } catch (error) {
      console.error("fetchVehicles:", error);
    }
  };

  useEffect(() => {
    if (clientId && token) {
      fetchVehicles();
    }
  }, [clientId, token]);

  const openModal = () => setModalOpen(true);
  const closeModal = () => {
    setModalOpen(false);
    setNewVehicle({ modelo: "", matricula: "", tipo: "" });
    setDynamicProps([]); // Limpiamos los campos dinámicos al cerrar
  };

  const handleInputChange = (
    e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>
  ) => {
    const { name, value } = e.target;
    setNewVehicle((prev) => ({ ...prev, [name]: value }));
  };

  // Maneja el cambio en los campos dinámicos
  const handleDynamicPropChange = (index: number, field: "key" | "value", value: string) => {
    const newDynamicProps = [...dynamicProps];
    newDynamicProps[index] = { ...newDynamicProps[index], [field]: value };
    setDynamicProps(newDynamicProps);
  };

  // Agrega un nuevo campo dinámico
  const addDynamicPropField = () => {
    setDynamicProps([...dynamicProps, { key: "", value: "" }]);
  };

  // Remueve un campo dinámico
  const removeDynamicPropField = (index: number) => {
    const newDynamicProps = dynamicProps.filter((_, idx) => idx !== index);
    setDynamicProps(newDynamicProps);
  };

  const handleAddVehicle = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    try {
      // Convertimos el array dynamicProps a un objeto
      const propiedadesAdicionales = dynamicProps.reduce((acc, prop) => {
        if (prop.key.trim() !== "") {
          acc[prop.key] = prop.value;
        }
        return acc;
      }, {} as { [key: string]: string });
      
      // Construimos el payload agregando el clienteId y las propiedades dinámicas
      const payload = { ...newVehicle, clienteId: Number(clientId), propiedadesAdicionales };
      
      const response = await fetch(`${API_URL}/vehiculos`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          "Authorization": `Bearer ${token}`,
        },
        body: JSON.stringify(payload),
      });
      if (!response.ok) {
        throw new Error("Error al agregar vehículo");
      }
      await response.json();
      fetchVehicles(); // Refresca la lista de vehículos
      closeModal();
    } catch (error) {
      console.error("handleAddVehicle:", error);
    }
  };

  return (
    <div className="p-6">
      <Link to="/dashboard" className="text-blue-500 underline mb-4 inline-block">
        &larr; Volver al Dashboard
      </Link>
      <h1 className="text-3xl font-bold text-[#007473] mb-4">
        Vehículos del Cliente {clientId}
      </h1>
      <button
        onClick={openModal}
        className="bg-[#007473] hover:bg-[#005f60] text-white px-4 py-2 rounded mb-4"
      >
        Agregar Vehículo
      </button>
      {vehicles.length === 0 ? (
        <p>No se encontraron vehículos para este cliente.</p>
      ) : (
        <ul className="space-y-2">
          {vehicles.map((vehiculo) => (
            <li
              key={vehiculo.id}
              className="p-4 border rounded flex flex-col md:flex-row md:justify-between md:items-center"
            >
              <div className="flex flex-col md:flex-row md:items-center md:space-x-4 flex-1">
                <p className="font-bold md:w-1/4">{vehiculo.modelo}</p>
                <p className="md:w-1/4">Matrícula: {vehiculo.matricula}</p>
                <p className="md:w-1/4">Tipo: {vehiculo.tipo}</p>
                {vehiculo.propiedadesAdicionales &&
                  Object.keys(vehiculo.propiedadesAdicionales).length > 0 && (
                    <div className="md:w-1/4 mt-2 md:mt-0">
                      <p className="font-semibold">Propiedades adicionales:</p>
                      <ul className="list-disc ml-5">
                        {Object.keys(vehiculo.propiedadesAdicionales).map((key) => (
                          <li key={key}>
                            {key}: {vehiculo.propiedadesAdicionales[key]}
                          </li>
                        ))}
                      </ul>
                    </div>
                  )}
              </div>
              <div className="mt-2 md:mt-0">
                <Link
                  to={`/turnos/${vehiculo.id}`}
                  className="bg-blue-500 hover:bg-blue-600 text-white px-4 py-2 rounded"
                >
                  Ver Turnos
                </Link>
              </div>
            </li>

          ))}
        </ul>
      )}

      {/* Modal para agregar vehículo */}
      {modalOpen && (
        <div className="fixed inset-0 flex items-center justify-center bg-black bg-opacity-50 z-50">
          <div className="bg-white rounded-lg p-6 w-96 overflow-y-auto max-h-screen">
            <h2 className="text-2xl font-bold mb-4">Agregar Vehículo</h2>
            <form onSubmit={handleAddVehicle} className="space-y-4">
              <div>
                <label className="block text-sm font-medium text-gray-700">
                  Modelo
                </label>
                <input
                  type="text"
                  name="modelo"
                  value={newVehicle.modelo}
                  onChange={handleInputChange}
                  className="mt-1 block w-full border border-gray-300 rounded-md p-2"
                  required
                />
              </div>
              <div>
                <label className="block text-sm font-medium text-gray-700">
                  Matrícula
                </label>
                <input
                  type="text"
                  name="matricula"
                  value={newVehicle.matricula}
                  onChange={handleInputChange}
                  className="mt-1 block w-full border border-gray-300 rounded-md p-2"
                  required
                />
              </div>
              <div>
                <label className="block text-sm font-medium text-gray-700">
                  Tipo
                </label>
                <select
                  name="tipo"
                  value={newVehicle.tipo}
                  onChange={handleInputChange}
                  className="mt-1 block w-full border border-gray-300 rounded-md p-2"
                  required
                >
                  <option value="">Seleccione...</option>
                  <option value="SEDAN">Sedán</option>
                  <option value="SUV">SUV</option>
                  <option value="CAMIONETA">Camioneta</option>
                </select>
              </div>
              {/* Sección para agregar campos de propiedades dinámicas */}
              <div>
                <div className="flex items-center justify-between">
                  <label className="block text-sm font-medium text-gray-700">
                    Propiedades adicionales
                  </label>
                  <button
                    type="button"
                    onClick={addDynamicPropField}
                    className="text-blue-500 underline text-sm"
                  >
                    Agregar propiedad
                  </button>
                </div>
                {dynamicProps.map((prop, index) => (
                  <div key={index} className="flex space-x-2 mt-2">
                    <input
                      type="text"
                      placeholder="Clave"
                      value={prop.key}
                      onChange={(e) => handleDynamicPropChange(index, "key", e.target.value)}
                      className="w-1/2 border border-gray-300 rounded-md p-2"
                    />
                    <input
                      type="text"
                      placeholder="Valor"
                      value={prop.value}
                      onChange={(e) => handleDynamicPropChange(index, "value", e.target.value)}
                      className="w-1/2 border border-gray-300 rounded-md p-2"
                    />
                    <button
                      type="button"
                      onClick={() => removeDynamicPropField(index)}
                      className="text-red-500"
                    >
                      X
                    </button>
                  </div>
                ))}
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
  );
};

export default ClientVehicles;
