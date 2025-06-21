package tech.munidigital.lavadero.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tech.munidigital.lavadero.entity.*;
import tech.munidigital.lavadero.entity.enums.EstadoTurno;
import tech.munidigital.lavadero.entity.enums.TipoServicio;
import tech.munidigital.lavadero.entity.enums.TipoVehiculo;
import tech.munidigital.lavadero.repository.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * Carga un conjunto de datos de ejemplo (usuarios, clientes, vehículos,
 * turnos y cobros) solo la primera vez que se inicia la aplicación en
 * entorno de desarrollo.  Deja el sistema listo para pruebas manuales.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

  /** E-mail que identifica si el seed ya fue insertado. */
  private static final String SEED_EMAIL = "lavadero@munidigital.com";

  private final UserRepository userRepo;
  private final ClienteRepository clienteRepo;
  private final VehiculoRepository vehiculoRepo;
  private final TurnoRepository turnoRepo;
  private final CobroRepository cobroRepo;
  private final PasswordEncoder encoder;

  /**
   * Ejecuta el seed en el arranque. Si el usuario demo existe se omite
   * para evitar duplicados.
   *
   * @param args argumentos de línea de comandos ignorados.
   */
  @Override
  @Transactional
  public void run(String... args) {
    if (userRepo.existsByEmail(SEED_EMAIL)) {
      log.info("-----> Seed ya cargado anteriormente. Skip.");
      return;
    }

    log.info("-----> Cargando datos iniciales dev…");
    cargarSeed();
    log.info("-----> Seed dev completado con éxito.");
  }

  /**
   * Inserta un usuario administrativo, cinco clientes, doce vehículos
   * y para cada vehículo tres turnos (dos finalizados, uno pendiente)
   * con sus respectivos cobros.
   */
  private void cargarSeed() {

    /* ---------- 1) Usuario demo ---------- */
    User muniUser = User.builder()
        .firstName("Muni")
        .lastName("Digital")
        .email(SEED_EMAIL)
        .phone("1234512345")
        .password(encoder.encode("12345Aa*"))
        .build();
    userRepo.save(muniUser);

    /* ---------- 2) Clientes ---------- */
    // 1 ─ Juan
    Cliente juan = clienteRepo.save(Cliente.builder()
        .nombre("Juan Pérez")
        .correoElectronico("juan.perez@gmail.com")
        .telefono("3512341234")
        .build());

    // 2 ─ María
    Cliente maria = clienteRepo.save(Cliente.builder()
        .nombre("María Gómez")
        .correoElectronico("maria.gomez@gmail.com")
        .telefono("3543214321")
        .build());

    // 3 ─ Carlos
    Cliente carlos = clienteRepo.save(Cliente.builder()
        .nombre("Carlos López")
        .correoElectronico("carlos.lopez@gmail.com")
        .telefono("3566442211")
        .build());

    // 4 ─ Ana
    Cliente ana = clienteRepo.save(Cliente.builder()
        .nombre("Ana Torres")
        .correoElectronico("ana.torres@gmail.com")
        .telefono("3522678899")
        .build());

    // 5 ─ Diego
    Cliente diego = clienteRepo.save(Cliente.builder()
        .nombre("Diego Martínez")
        .correoElectronico("diego.martinez@gmail.com")
        .telefono("3519988776")
        .build());

    /* ---------- 3) Vehículos (12) ---------- */
    // --- Juan (3) ---
    Vehiculo corolla = vehiculoRepo.save(Vehiculo.builder()
        .modelo("Toyota Corolla")
        .matricula("AAZ123")
        .tipo(TipoVehiculo.SEDAN)
        .cliente(juan)
        .propiedadesAdicionales(Map.of(
            "color", "Rojo",
            "anio", 2018,
            "combustible", "Nafta",
            "transmision", "Automática"))
        .build());

    Vehiculo ranger = vehiculoRepo.save(Vehiculo.builder()
        .modelo("Ford Ranger")
        .matricula("BBQ456")
        .tipo(TipoVehiculo.CAMIONETA)
        .cliente(juan)
        .propiedadesAdicionales(Map.of(
            "color", "Gris",
            "anio", 2022,
            "combustible", "Diésel",
            "traccion", "4x4"))
        .build());

    Vehiculo kwid = vehiculoRepo.save(Vehiculo.builder()
        .modelo("Renault Kwid")
        .matricula("CCP789")
        .tipo(TipoVehiculo.SEDAN)
        .cliente(juan)
        .propiedadesAdicionales(Map.of(
            "color", "Blanco",
            "anio", 2020))
        .build());

    // --- María (3) ---
    Vehiculo tracker = vehiculoRepo.save(Vehiculo.builder()
        .modelo("Chevrolet Tracker")
        .matricula("DDT321")
        .tipo(TipoVehiculo.SUV)
        .cliente(maria)
        .propiedadesAdicionales(Map.of(
            "color", "Azul",
            "anio", 2021,
            "techo", "Panorámico"))
        .build());

    Vehiculo civic = vehiculoRepo.save(Vehiculo.builder()
        .modelo("Honda Civic")
        .matricula("EEH654")
        .tipo(TipoVehiculo.SEDAN)
        .cliente(maria)
        .propiedadesAdicionales(Map.of(
            "color", "Negro",
            "anio", 2019,
            "combustible", "Nafta"))
        .build());

    Vehiculo cronos = vehiculoRepo.save(Vehiculo.builder()
        .modelo("Fiat Cronos")
        .matricula("FFI987")
        .tipo(TipoVehiculo.SEDAN)
        .cliente(maria)
        .propiedadesAdicionales(Map.of(
            "color", "Plateado",
            "anio", 2023,
            "pack_seguridad", true))
        .build());

    // --- Carlos (2) ---
    Vehiculo peugeot208 = vehiculoRepo.save(Vehiculo.builder()
        .modelo("Peugeot 208")
        .matricula("GGJ258")
        .tipo(TipoVehiculo.SEDAN)
        .cliente(carlos)
        .propiedadesAdicionales(Map.of(
            "color", "Verde",
            "anio", 2017))
        .build());

    Vehiculo ecosport = vehiculoRepo.save(Vehiculo.builder()
        .modelo("Ford EcoSport")
        .matricula("HHK369")
        .tipo(TipoVehiculo.SUV)
        .cliente(carlos)
        .propiedadesAdicionales(Map.of(
            "color", "Rojo",
            "anio", 2016,
            "combustible", "Nafta"))
        .build());

    // --- Ana (2) ---
    Vehiculo gol = vehiculoRepo.save(Vehiculo.builder()
        .modelo("Volkswagen Gol")
        .matricula("IIL147")
        .tipo(TipoVehiculo.SEDAN)
        .cliente(ana)
        .propiedadesAdicionales(Map.of(
            "color", "Gris",
            "anio", 2015))
        .build());

    Vehiculo hilux = vehiculoRepo.save(Vehiculo.builder()
        .modelo("Toyota Hilux")
        .matricula("JJM753")
        .tipo(TipoVehiculo.CAMIONETA)
        .cliente(ana)
        .propiedadesAdicionales(Map.of(
            "color", "Negro",
            "anio", 2024,
            "traccion", "4x4"))
        .build());

    // --- Diego (2) ---
    Vehiculo versa = vehiculoRepo.save(Vehiculo.builder()
        .modelo("Nissan Versa")
        .matricula("KKN852")
        .tipo(TipoVehiculo.SEDAN)
        .cliente(diego)
        .propiedadesAdicionales(Map.of(
            "color", "Azul Oscuro",
            "anio", 2018,
            "gps", true))
        .build());

    Vehiculo onix = vehiculoRepo.save(Vehiculo.builder()
        .modelo("Chevrolet Onix")
        .matricula("LLP963")
        .tipo(TipoVehiculo.SEDAN)
        .cliente(diego)
        .propiedadesAdicionales(Map.of(
            "color", "Blanco",
            "anio", 2022,
            "pack_premium", true))
        .build());

    /* ---------- 4) Turnos y Cobros (2 turnos por vehículo, 1 cobro) ---------- */
    crearTurnosYCobroParaVehiculo(corolla);
    crearTurnosYCobroParaVehiculo(ranger);
    crearTurnosYCobroParaVehiculo(kwid);
    crearTurnosYCobroParaVehiculo(tracker);
    crearTurnosYCobroParaVehiculo(civic);
    crearTurnosYCobroParaVehiculo(cronos);
    crearTurnosYCobroParaVehiculo(peugeot208);
    crearTurnosYCobroParaVehiculo(ecosport);
    crearTurnosYCobroParaVehiculo(gol);
    crearTurnosYCobroParaVehiculo(hilux);
    crearTurnosYCobroParaVehiculo(versa);
    crearTurnosYCobroParaVehiculo(onix);
  }

  /**
   * Registra tres turnos por vehículo (uno futuro y dos pasados)
   * y crea los cobros correspondientes para los turnos finalizados.
   *
   * @param vehiculo vehículo al que se asociarán los turnos.
   */
  private void crearTurnosYCobroParaVehiculo(Vehiculo vehiculo) {

    // ---------- PENDIENTE a futuro ----------
    turnoRepo.save(Turno.builder()
        .fechaHora(LocalDateTime.now().plusDays(2))
        .estado(EstadoTurno.PENDIENTE)
        .tipoServicio(TipoServicio.LAVADO_COMPLETO)
        .vehiculo(vehiculo)
        .build());

    // ---------- FINALIZADO: LAVADO EXTERIOR ----------
    Turno exterior = turnoRepo.save(Turno.builder()
        .fechaHora(LocalDateTime.now().minusDays(3))
        .estado(EstadoTurno.FINALIZADO)
        .tipoServicio(TipoServicio.LAVADO_EXTERIOR)
        .vehiculo(vehiculo)
        .build());

    Cobro cobroExterior = Cobro.builder()
        .monto(BigDecimal.valueOf(12_500))
        .fecha(LocalDate.now())
        .turno(exterior)
        .build();
    cobroRepo.save(cobroExterior);
    exterior.setCobro(cobroExterior);

    // ---------- FINALIZADO: LAVADO INTERIOR ----------
    Turno interior = turnoRepo.save(Turno.builder()
        .fechaHora(LocalDateTime.now().minusDays(2))
        .estado(EstadoTurno.FINALIZADO)
        .tipoServicio(TipoServicio.LAVADO_INTERIOR)
        .vehiculo(vehiculo)
        .build());

    Cobro cobroInterior = Cobro.builder()
        .monto(BigDecimal.valueOf(14_999))
        .fecha(LocalDate.now())
        .turno(interior)
        .build();
    cobroRepo.save(cobroInterior);
    interior.setCobro(cobroInterior);
  }

}
