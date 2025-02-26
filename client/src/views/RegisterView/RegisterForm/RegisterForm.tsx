import React, { useState } from 'react';
import { useForm, SubmitHandler } from 'react-hook-form';
import { IRegisterFormInput } from '../../../types/Types';
import useAuth from '../../../services/Api';
import { useNavigate } from 'react-router-dom';
import { toast } from 'react-toastify';
import Spinner from '../../../components/Spinner';

export const RegisterForm: React.FC = () => {
  const { register, handleSubmit, watch, formState: { errors, isValid } } = useForm<IRegisterFormInput>({ mode: 'onChange' });
  const { register: registerUser } = useAuth();
  const navigate = useNavigate();
  const [isLoading, setIsLoading] = useState(false);
  const password = watch("password", "");

  const onSubmit: SubmitHandler<IRegisterFormInput> = async (data) => {
    setIsLoading(true);
    try {
      // Se envían los campos: email, password, firstName, lastName y phone
      await registerUser(data.email, data.password, data.firstName, data.lastName, data.phone);
      toast.success("Registro exitoso");
      navigate('/login');
    } catch (error: unknown) {
      if (error instanceof Error) {
        toast.error("Error en el registro: " + error.message);
      } else {
        console.error("Error inesperado:", error);
      }
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <form onSubmit={handleSubmit(onSubmit)} className="space-y-4 md:space-y-4" autoComplete="off">
      {/* Campo: First Name */}
      <div className="w-full">
        <label htmlFor="firstName" className="block mb-2 text-base font-medium text-[#007473]">
          Nombre
        </label>
        <input
          type="text"
          id="firstName"
          {...register("firstName", {
            required: "*Nombre es requerido",
            pattern: { value: /^[a-zA-Z\s]+$/, message: "*El nombre solo debe contener letras y espacios" }
          })}
          className={`bg-[#D9D9D9] border ${errors.firstName ? 'border-red-600' : 'border-[#007473]'} text-gray-900 sm:text-sm md:text-base rounded-lg focus:ring-[#005f60] focus:border-[#005f60] block w-full p-2.5 shadow-sm`}
        />
        {errors.firstName && <p className="mt-2 text-sm text-red-600">{errors.firstName.message}</p>}
      </div>

      {/* Campo: Last Name */}
      <div className="w-full">
        <label htmlFor="lastName" className="block mb-2 text-base font-medium text-[#007473]">
          Apellido
        </label>
        <input
          type="text"
          id="lastName"
          {...register("lastName", {
            required: "*Apellido es requerido",
            pattern: { value: /^[a-zA-Z\s]+$/, message: "*El apellido solo debe contener letras y espacios" }
          })}
          className={`bg-[#D9D9D9] border ${errors.lastName ? 'border-red-600' : 'border-[#007473]'} text-gray-900 sm:text-sm md:text-base rounded-lg focus:ring-[#005f60] focus:border-[#005f60] block w-full p-2.5 shadow-sm`}
        />
        {errors.lastName && <p className="mt-2 text-sm text-red-600">{errors.lastName.message}</p>}
      </div>

      {/* Campo: Teléfono */}
      <div className="w-full">
        <label htmlFor="phone" className="block mb-2 text-base font-medium text-[#007473]">
          Teléfono
        </label>
        <input
          type="text"
          id="phone"
          {...register("phone", {
            required: "*Teléfono es requerido",
            pattern: { value: /^[0-9\-+]{9,15}$/, message: "*El número de teléfono no es válido" }
          })}
          className={`bg-[#D9D9D9] border ${errors.phone ? 'border-red-600' : 'border-[#007473]'} text-gray-900 sm:text-sm md:text-base rounded-lg focus:ring-[#005f60] focus:border-[#005f60] block w-full p-2.5 shadow-sm`}
        />
        {errors.phone && <p className="mt-2 text-sm text-red-600">{errors.phone.message}</p>}
      </div>

      {/* Campo: Email */}
      <div className="w-full">
        <label htmlFor="email" className="block mb-2 text-base font-medium text-[#007473]">
          Email
        </label>
        <input
          type="email"
          id="email"
          {...register("email", {
            required: "*Email es requerido",
            pattern: { value: /^\S+@\S+$/i, message: "*Dirección de email no válida" },
            maxLength: { value: 50, message: "*Email no debe exceder 50 caracteres" }
          })}
          className={`bg-[#D9D9D9] border ${errors.email ? 'border-red-600' : 'border-[#007473]'} text-gray-900 sm:text-sm md:text-base rounded-lg focus:ring-[#005f60] focus:border-[#005f60] block w-full p-2.5 shadow-sm`}
        />
        {errors.email && <p className="mt-2 text-sm text-red-600">{errors.email.message}</p>}
      </div>

      {/* Campo: Contraseña */}
      <div className="w-full">
        <label htmlFor="password" className="block mb-2 text-base font-medium text-[#007473]">
          Contraseña
        </label>
        <input
          type="password"
          id="password"
          {...register("password", {
            required: "*Contraseña es requerida",
            minLength: { value: 6, message: "*Contraseña debe tener al menos 6 caracteres" },
            pattern: {
              value: /^(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]+$/,
              message: "*La contraseña debe contener al menos una mayúscula, un número y un carácter especial"
            }
          })}
          className={`bg-[#D9D9D9] border ${errors.password ? 'border-red-600' : 'border-[#007473]'} text-gray-900 sm:text-sm md:text-base rounded-lg focus:ring-[#005f60] focus:border-[#005f60] block w-full p-2.5 shadow-sm`}
        />
        {errors.password && <p className="mt-2 text-sm text-red-600">{errors.password.message}</p>}
      </div>

      {/* Campo: Confirmar Contraseña */}
      <div className="w-full">
        <label htmlFor="confirmPassword" className="block mb-2 text-base font-medium text-[#007473]">
          Repetir Contraseña
        </label>
        <input
          type="password"
          id="confirmPassword"
          {...register("confirmPassword", {
            required: "*Por favor, confirme su contraseña",
            validate: value => value === password || "Las contraseñas no coinciden"
          })}
          className={`bg-[#D9D9D9] border ${errors.confirmPassword ? 'border-red-600' : 'border-[#007473]'} text-gray-900 sm:text-sm md:text-base rounded-lg focus:ring-[#005f60] focus:border-[#005f60] block w-full p-2.5 shadow-sm`}
        />
        {errors.confirmPassword && <p className="mt-2 text-sm text-red-600">{errors.confirmPassword.message}</p>}
      </div>

      <div className="mt-16 text-center">
        <button
          type="submit"
          className="cursor-pointer w-full text-white bg-[#007473] hover:bg-[#005f60] focus:ring-4 focus:outline-none focus:ring-[#005f60] font-medium rounded-lg text-sm md:text-lg px-5 py-2.5 text-center shadow-md hover:shadow-lg transition duration-150 ease-in-out"
          disabled={!isValid || isLoading}
        >
          {isLoading ? <Spinner /> : "Registrar"}
        </button>
        <div className="text-sm text-center text-[#007473] mt-4">
          ¿Ya tienes cuenta? <a href="/login" className="font-medium text-[#007473] hover:underline">Iniciar Sesión</a>
        </div>
      </div>
    </form>
  );
};
