import React from "react";

const AboutSection: React.FC = () => {
  return (
    <section className="bg-white lg:h-screen h-full w-full py-16" id="about">
      <div className="max-w-screen-xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="lg:text-center">
          <h2 className="text-base text-[#007473] font-semibold tracking-wide uppercase">
            Acerca de Nosotros
          </h2>
          <p className="mt-2 text-3xl leading-8 font-extrabold tracking-tight text-gray-900 sm:text-4xl">
            Calidad y Eficiencia en el Cuidado de tu Vehículo
          </p>
          <p className="mt-4 max-w-2xl text-xl text-gray-700 lg:mx-auto">
            Lavadero de Autos es una plataforma innovadora que te ayuda a gestionar de forma integral el lavado y mantenimiento de tu vehículo, combinando la experiencia en servicios automotrices con la tecnología de MuniDigital.
          </p>
        </div>
        <div className="mt-10">
          <dl className="space-y-10 md:space-y-0 md:grid md:grid-cols-2 md:gap-x-8 md:gap-y-10">
            {/* Misión */}
            <div className="relative">
              <dt>
                <div className="absolute flex items-center justify-center h-12 w-12 rounded-md bg-[#007473] text-white">
                  <svg
                    className="h-6 w-6"
                    fill="none"
                    stroke="currentColor"
                    viewBox="0 0 24 24"
                    xmlns="http://www.w3.org/2000/svg"
                  >
                    <path
                      strokeLinecap="round"
                      strokeLinejoin="round"
                      strokeWidth="2"
                      d="M20 12H4"
                    ></path>
                  </svg>
                </div>
                <p className="ml-16 text-lg leading-6 font-medium text-gray-900">
                  Nuestra Misión
                </p>
              </dt>
              <dd className="mt-2 ml-16 text-base text-gray-700 font-medium">
                Brindar un servicio de lavado de autos eficiente, confiable y de alta calidad, para que cada cliente disfrute de un vehículo impecable.
              </dd>
            </div>
            {/* Visión */}
            <div className="relative">
              <dt>
                <div className="absolute flex items-center justify-center h-12 w-12 rounded-md bg-[#007473] text-white">
                  <svg
                    className="h-6 w-6"
                    fill="none"
                    stroke="currentColor"
                    viewBox="0 0 24 24"
                    xmlns="http://www.w3.org/2000/svg"
                  >
                    <path
                      strokeLinecap="round"
                      strokeLinejoin="round"
                      strokeWidth="2"
                      d="M12 8v4l3 3"
                    ></path>
                  </svg>
                </div>
                <p className="ml-16 text-lg leading-6 font-medium text-gray-900">
                  Nuestra Visión
                </p>
              </dt>
              <dd className="mt-2 ml-16 text-base text-gray-700 font-medium">
                Ser la opción líder en servicios de lavado de autos, integrando innovación tecnológica para simplificar la gestión y el mantenimiento de vehículos.
              </dd>
            </div>
            {/* Valores */}
            <div className="relative">
              <dt>
                <div className="absolute flex items-center justify-center h-12 w-12 rounded-md bg-[#007473] text-white">
                  <svg
                    className="h-6 w-6"
                    fill="none"
                    stroke="currentColor"
                    viewBox="0 0 24 24"
                    xmlns="http://www.w3.org/2000/svg"
                  >
                    <path
                      strokeLinecap="round"
                      strokeLinejoin="round"
                      strokeWidth="2"
                      d="M17 9V3H7v6H5v9h14V9h-2z"
                    ></path>
                  </svg>
                </div>
                <p className="ml-16 text-lg leading-6 font-medium text-gray-900">
                  Nuestros Valores
                </p>
              </dt>
              <dd className="mt-2 ml-16 text-base text-gray-700 font-medium">
                Compromiso, calidad, innovación y transparencia son la base de nuestro servicio, asegurando la satisfacción de cada cliente.
              </dd>
            </div>
            {/* Historia */}
            <div className="relative">
              <dt>
                <div className="absolute flex items-center justify-center h-12 w-12 rounded-md bg-[#007473] text-white">
                  <svg
                    className="h-6 w-6"
                    fill="none"
                    stroke="currentColor"
                    viewBox="0 0 24 24"
                    xmlns="http://www.w3.org/2000/svg"
                  >
                    <path
                      strokeLinecap="round"
                      strokeLinejoin="round"
                      strokeWidth="2"
                      d="M9 5h6M9 9h6M9 13h6M9 17h6"
                    ></path>
                  </svg>
                </div>
                <p className="ml-16 text-lg leading-6 font-medium text-gray-900">
                  Nuestra Historia
                </p>
              </dt>
              <dd className="mt-2 ml-16 text-base text-gray-700 font-medium">
                Fundada en 2024, fusionamos la experiencia en servicios automotrices con la tecnología de MuniDigital para revolucionar el cuidado y mantenimiento de vehículos.
              </dd>
            </div>
          </dl>
        </div>
      </div>
    </section>
  );
};

export default AboutSection;
