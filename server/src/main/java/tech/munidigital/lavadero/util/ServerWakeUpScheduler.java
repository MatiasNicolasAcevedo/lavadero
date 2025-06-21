package tech.munidigital.lavadero.util;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Scheduler que mantiene “despierto” el servidor alojado en Render
 * para evitar el cold-start tras periodos de inactividad.
 *
 * <p>Cada 14 minutos ejecuta un <i>health-check</i> contra el endpoint
 * <code>/v1/api/render/health</code>.  Si Render pasa a modo idle,
 * esta petición lo reactiva y reduce la latencia del primer request
 * real de los usuarios.</p>
 */
@Component
@EnableScheduling
public class ServerWakeUpScheduler {

  /**
   * URL base del backend desplegado en Render.
   */
  private final String serverUrl = "https://lavaderoweb.onrender.com";

  /**
   * Cliente HTTP simple de Spring.
   */
  private final RestTemplate restTemplate = new RestTemplate();

  /**
   * Job programado que se ejecuta cada 14 minutos
   * (<code>840 000 ms</code>) para mantener el servidor activo.
   *
   * <p>En caso de error se loguea la causa por <code>stderr</code>;
   * no se relanza la excepción para no interrumpir el scheduler.</p>
   */
  @Scheduled(fixedRate = 840_000) // 14 minutos
  public void keepServerAlive() {
    String healthUrl = serverUrl + "/v1/api/render/health";

    try {
      String result = restTemplate.getForObject(healthUrl, String.class);
      System.out.println("Health Check Result: " + result);
    } catch (Exception e) {
      System.err.println("Error al realizar el Health Check: " + e.getMessage());
    }
  }

}