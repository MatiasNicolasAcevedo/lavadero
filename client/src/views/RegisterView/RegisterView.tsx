import { RegisterForm } from "./RegisterForm/RegisterForm";

const RegisterView = () => {
  return (
    <section className="bg-gradient-to-r from-[#007473] via-[#006B66] to-[#005f60] min-h-screen flex items-center">
      <div className="flex flex-col items-center justify-center px-6 py-8 mx-auto w-full md:w-70 lg:w-3/5 xl:w-2/5 md:items-end">
        <div className="w-full bg-white rounded-lg shadow-lg md:mt-0 xl:p-0">
          <div className="p-6 space-y-1 md:space-y-3 sm:p-8">
            <h1 className="text-lg text-center font-bold leading-tight tracking-tight text-[#007473] pb-2 md:text-2xl">
              ¡Únete a Lavadero de Autos!
            </h1>
            <RegisterForm />
          </div>
        </div>
      </div>
    </section>
  );
};

export default RegisterView;
