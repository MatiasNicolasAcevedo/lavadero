import {
  Modal,
  ModalContent,
  ModalHeader,
  ModalBody,
  ModalFooter,
  Button,
  useDisclosure,
} from "@nextui-org/react";

export default function Terms() {
  const { isOpen, onOpen, onOpenChange } = useDisclosure();

  return (
    <>
      <Button onPress={onOpen} className="bg-transparent text-white">
        Términos y condiciones
      </Button>
      <Modal
        size={"lg"}
        scrollBehavior={"inside"}
        isOpen={isOpen}
        backdrop="opaque"
        onOpenChange={onOpenChange}
        isDismissable={true}
        isKeyboardDismissDisabled={true}
        motionProps={{
          variants: {
            enter: {
              y: 0,
              opacity: 1,
              transition: {
                duration: 0.3,
                ease: "easeOut",
              },
            },
            exit: {
              y: -20,
              opacity: 0,
              transition: {
                duration: 0.2,
                ease: "easeIn",
              },
            },
          },
        }}
      >
        <ModalContent>
          {(onClose) => (
            <>
              <ModalHeader className="flex flex-col gap-1">
                Términos y Condiciones de Lavadero de Autos
              </ModalHeader>
              <ModalBody>
                <p>
                  <span className="font-bold">¡Bienvenido a Lavadero de Autos!</span>
                  <br />
                  Lavadero de Autos es una plataforma que te permite gestionar de manera
                  eficiente el lavado y mantenimiento de vehículos. Al utilizar nuestros
                  servicios, aceptas cumplir con los siguientes términos y condiciones. Si
                  no estás de acuerdo, por favor, no utilices nuestra plataforma.
                </p>
                <p className="mt-3">
                  <span className="font-bold">1. Definiciones:</span>
                  <br />- <span className="font-bold">Cliente:</span> Persona que registra su vehículo para recibir el servicio.
                  <br />- <span className="font-bold">Vehículo:</span> Automóvil registrado en el sistema.
                  <br />- <span className="font-bold">Turno:</span> Cita programada para el lavado del vehículo.
                  <br />- <span className="font-bold">Cobro:</span> Pago en efectivo asociado a un turno completado.
                </p>
                <p className="mt-3">
                  <span className="font-bold">2. Uso de la Plataforma:</span>
                  <br />
                  2.1. <span className="font-bold">Elegibilidad:</span> Debes tener capacidad legal para solicitar nuestros servicios.
                  <br />
                  2.2. <span className="font-bold">Registro:</span> Es necesario proporcionar información precisa y actual al registrarte.
                </p>
                <p className="mt-3">
                  <span className="font-bold">3. Turnos y Servicios:</span>
                  <br />
                  Los turnos deben programarse con anticipación y se realizará el lavado del vehículo en el horario indicado. Es responsabilidad del cliente cumplir con el turno asignado.
                </p>
                <p className="mt-3">
                  <span className="font-bold">4. Cobros:</span>
                  <br />
                  El cobro se efectúa únicamente después de la finalización del turno y se realiza en efectivo. Asegúrate de confirmar el monto antes de proceder al pago.
                </p>
                <p className="mt-3 font-thin">
                  Al utilizar los servicios de Lavadero de Autos, confirmas haber leído, comprendido y aceptado estos términos y condiciones en su totalidad. Si tienes alguna pregunta, no dudes en contactarnos.
                </p>
              </ModalBody>
              <ModalFooter>
                <Button color="danger" variant="light" onPress={onClose}>
                  Cerrar
                </Button>
              </ModalFooter>
            </>
          )}
        </ModalContent>
      </Modal>
    </>
  );
}
