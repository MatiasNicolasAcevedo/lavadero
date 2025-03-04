import React, { useEffect, useState } from "react";
import { useParams, Link } from "react-router-dom";
import useAuth from "../services/Api";

interface Cobro {
  id: number;
  monto: number;
  fecha: string; // asumiendo formato ISO (yyyy-MM-dd)
  turnoId: number;
}

interface Turno {
  id: number;
  fechaHora: string; // en formato ISO
  estado: string;
  tipoServicio: string;
  vehiculoId: number;
  cobro?: Cobro; // información del cobro, si existe
}

const API_URL = "https://lavaderoweb.onrender.com/v1/api";

const Turnos: React.FC = () => {
  const { vehiculoId } = useParams<{ vehiculoId: string }>();
  const { token } = useAuth();
  const [turnos, setTurnos] = useState<Turno[]>([]);
  const [modalOpen, setModalOpen] = useState<boolean>(false);
  const [newTurno, setNewTurno] = useState({
    fechaHora: "",
    tipoServicio: "",
    estado: "PENDIENTE", // estado por defecto
    vehiculoId: Number(vehiculoId),
  });

  // Estados para el modal de cobro
  const [cobroModalOpen, setCobroModalOpen] = useState<boolean>(false);
  const [selectedTurnoId, setSelectedTurnoId] = useState<number | null>(null);
  const [newCobro, setNewCobro] = useState({
    monto: "",
    fecha: "",
  });

  // Función para obtener los turnos asociados a un vehículo
  const fetchTurnos = async () => {
    try {
      const response = await fetch(`${API_URL}/turnos/vehiculo/${vehiculoId}`, {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
      });
      if (!response.ok) {
        throw new Error("Error al obtener los turnos");
      }
      const data = await response.json();
      setTurnos(data);
    } catch (error) {
      console.error("fetchTurnos:", error);
    }
  };

  useEffect(() => {
    if (vehiculoId && token) {
      fetchTurnos();
    }
  }, [vehiculoId, token]);

  const openModal = () => setModalOpen(true);
  const closeModal = () => {
    setModalOpen(false);
    setNewTurno({
      fechaHora: "",
      tipoServicio: "",
      estado: "PENDIENTE",
      vehiculoId: Number(vehiculoId),
    });
  };

  const handleInputChange = (
    e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>
  ) => {
    const { name, value } = e.target;
    setNewTurno((prev) => ({ ...prev, [name]: value }));
  };

  const handleAddTurno = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    try {
      const payload = { ...newTurno, vehiculoId: Number(vehiculoId) };
      const response = await fetch(`${API_URL}/turnos`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(payload),
      });
      if (!response.ok) {
        throw new Error("Error al crear turno");
      }
      await response.json();
      fetchTurnos(); // refrescamos la lista
      closeModal();
    } catch (error) {
      console.error("handleAddTurno:", error);
    }
  };

  // Función para actualizar el estado de un turno
  const updateTurnoState = async (id: number, newState: string) => {
    try {
      let endpoint = "";
      if (newState === "EN_PROCESO") {
        endpoint = `${API_URL}/turnos/${id}/en-proceso`;
      } else if (newState === "FINALIZADO") {
        endpoint = `${API_URL}/turnos/${id}/finalizado`;
      } else {
        throw new Error("Estado no soportado");
      }
      const response = await fetch(endpoint, {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
      });
      if (!response.ok) {
        throw new Error("Error al actualizar el estado del turno");
      }
      await response.json();
      fetchTurnos();
    } catch (error) {
      console.error("updateTurnoState:", error);
    }
  };

  // Funciones para el modal de cobro
  const openCobroModal = (turnoId: number) => {
    setSelectedTurnoId(turnoId);
    setCobroModalOpen(true);
  };

  const closeCobroModal = () => {
    setCobroModalOpen(false);
    setSelectedTurnoId(null);
    setNewCobro({
      monto: "",
      fecha: "",
    });
  };

  const handleCobroInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setNewCobro((prev) => ({ ...prev, [name]: value }));
  };

  const handleAddCobro = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    try {
      if (selectedTurnoId === null) return;
      const payload = {
        ...newCobro,
        // Convertir monto a número (o dejarlo tal cual si el backend lo procesa)
        monto: parseFloat(newCobro.monto),
        turnoId: selectedTurnoId,
      };
      const response = await fetch(`${API_URL}/cobros`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(payload),
      });
      if (!response.ok) {
        throw new Error("Error al crear cobro");
      }
      await response.json();
      fetchTurnos();
      closeCobroModal();
    } catch (error) {
      console.error("handleAddCobro:", error);
    }
  };

  return (
    <div className="min-h-screen flex flex-col">
      <div className="flex-grow p-6">
        <Link to="/dashboard" className="text-blue-500 underline mb-4 inline-block">
          &larr; Volver al Dashboard
        </Link>
        <h1 className="text-3xl font-bold text-[#007473] mb-4">
          Turnos del Vehículo {vehiculoId}
        </h1>
        <button
          onClick={openModal}
          className="bg-[#007473] hover:bg-[#005f60] text-white px-4 py-2 rounded mb-4"
        >
          Agregar Turno
        </button>
        {turnos.length === 0 ? (
          <p>No se encontraron turnos para este vehículo.</p>
        ) : (
          <ul className="space-y-2">
            {turnos.map((turno) => (
              <li
                key={turno.id}
                className="p-4 border rounded flex flex-col md:flex-row"
              >
                {/* Columna de información */}
                <div className="flex flex-col space-y-1 md:w-4/5">
                  <p className="font-bold">Turno ID: {turno.id}</p>
                  <p>Fecha y Hora: {new Date(turno.fechaHora).toLocaleString()}</p>
                  <p>Tipo de Servicio: {turno.tipoServicio}</p>
                  <p>Estado: {turno.estado}</p>
                </div>

                {/* Columna de acciones y cobro */}
                <div className="mt-4 md:mt-2 md:w-1/5 md:pl-4 flex flex-col items-start md:items-center justify-center">
                  {/* Botones para iniciar/finalizar turno y generar cobro */}
                  <div className="flex flex-col items-center">
                    {turno.estado === "PENDIENTE" && (
                      <button
                        onClick={() => updateTurnoState(turno.id, "EN_PROCESO")}
                        className="bg-blue-500 hover:bg-blue-600 text-white px-3 py-1 rounded"
                      >
                        Iniciar Turno
                      </button>
                    )}
                    {turno.estado === "EN_PROCESO" && (
                      <button
                        onClick={() => updateTurnoState(turno.id, "FINALIZADO")}
                        className="bg-green-500 hover:bg-green-600 text-white px-3 py-1 rounded"
                      >
                        Finalizar Turno
                      </button>
                    )}
                    {turno.estado === "FINALIZADO" && !turno.cobro && (
                      <button
                        onClick={() => openCobroModal(turno.id)}
                        className="mt-2 bg-purple-500 hover:bg-purple-600 text-white px-3 py-1 rounded"
                      >
                        Generar Cobro
                      </button>
                    )}
                  </div>

                  {/* Si existe el cobro, se muestra la información debajo */}
                  {turno.cobro && (
                    <div className="w-full text-left mt-2">
                      <p className="font-semibold">Cobro:</p>
                      <p>ID: {turno.cobro.id}</p>
                      <p>Monto: {turno.cobro.monto}</p>
                      <p>
                        Fecha: {new Date(turno.cobro.fecha).toLocaleDateString()}
                      </p>
                    </div>
                  )}
                </div>


              </li>

            ))}
          </ul>
        )}

        {/* Modal para agregar turno */}
        {modalOpen && (
          <div className="fixed inset-0 flex items-center justify-center bg-black bg-opacity-50 z-50">
            <div className="bg-white rounded-lg p-6 w-96">
              <h2 className="text-2xl font-bold mb-4">Agregar Turno</h2>
              <form onSubmit={handleAddTurno} className="space-y-4">
                <div>
                  <label className="block text-sm font-medium text-gray-700">
                    Fecha y Hora
                  </label>
                  <input
                    type="datetime-local"
                    name="fechaHora"
                    value={newTurno.fechaHora}
                    onChange={handleInputChange}
                    className="mt-1 block w-full border border-gray-300 rounded-md p-2"
                    required
                  />
                </div>
                <div>
                  <label className="block text-sm font-medium text-gray-700">
                    Tipo de Servicio
                  </label>
                  <select
                    name="tipoServicio"
                    value={newTurno.tipoServicio}
                    onChange={handleInputChange}
                    className="mt-1 block w-full border border-gray-300 rounded-md p-2"
                    required
                  >
                    <option value="">Seleccione...</option>
                    <option value="LAVADO_EXTERIOR">
                      Lavado Exterior
                    </option>
                    <option value="INTERIOR">
                      Lavado Interior
                    </option>
                    <option value="AMBOS">Ambos</option>
                  </select>
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

        {/* Modal para agregar cobro */}
        {cobroModalOpen && (
          <div className="fixed inset-0 flex items-center justify-center bg-black bg-opacity-50 z-50">
            <div className="bg-white rounded-lg p-6 w-96">
              <h2 className="text-2xl font-bold mb-4">Generar Cobro</h2>
              <form onSubmit={handleAddCobro} className="space-y-4">
                <div>
                  <label className="block text-sm font-medium text-gray-700">
                    Monto
                  </label>
                  <input
                    type="number"
                    step="0.01"
                    name="monto"
                    value={newCobro.monto}
                    onChange={handleCobroInputChange}
                    className="mt-1 block w-full border border-gray-300 rounded-md p-2"
                    required
                  />
                </div>
                <div>
                  <label className="block text-sm font-medium text-gray-700">
                    Fecha
                  </label>
                  <input
                    type="date"
                    name="fecha"
                    value={newCobro.fecha}
                    onChange={handleCobroInputChange}
                    className="mt-1 block w-full border border-gray-300 rounded-md p-2"
                    required
                  />
                </div>
                <div className="flex justify-end space-x-4">
                  <button
                    type="button"
                    onClick={closeCobroModal}
                    className="bg-gray-300 hover:bg-gray-400 text-gray-800 px-4 py-2 rounded"
                  >
                    Cancelar
                  </button>
                  <button
                    type="submit"
                    className="bg-purple-500 hover:bg-purple-600 text-white px-4 py-2 rounded"
                  >
                    Crear Cobro
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

export default Turnos;
