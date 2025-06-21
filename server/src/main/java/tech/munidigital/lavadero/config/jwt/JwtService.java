package tech.munidigital.lavadero.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import tech.munidigital.lavadero.entity.User;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Servicio utilitario para la generación y validación de JSON Web Tokens (JWT)
 * dentro del contexto de seguridad de la aplicación.
 */
@Service
public class JwtService {

  /**
   * Clave secreta en Base64 configurada vía aplicación o variable de entorno.
   */
  @Value("${SECRET}")
  private String secretKey;

  /**
   * Genera un JWT para un usuario sin claims adicionales.
   *
   * @param user entidad {@link User} a la que se asocia el token.
   * @return token JWT firmado.
   */
  public String generateToken(User user) {
    return generateToken(new HashMap<>(), user);
  }

  /**
   * Genera un JWT para un usuario, permitiendo incluir claims personalizados.
   *
   * @param extraClaims mapa de claims extra que se agregarán al payload.
   * @param user        usuario destinatario del token.
   * @return token JWT firmado.
   */
  public String generateToken(Map<String, Object> extraClaims, User user) {
    return Jwts.builder()
        .setClaims(extraClaims)
        .setSubject(user.getUsername())
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 10)) // 10 días
        .signWith(getSigningKey(), SignatureAlgorithm.HS256)
        .compact();
  }

  /**
   * Obtiene el nombre de usuario (subject) almacenado en el token.
   *
   * @param token JWT del que se extrae el subject.
   * @return nombre de usuario.
   */
  public String getUserName(String token) {
    return getClaim(token, Claims::getSubject);
  }

  /**
   * Obtiene un claim arbitrario aplicando una función de resolución sobre
   * el objeto {@link Claims}.
   *
   * @param token          JWT origen.
   * @param claimsResolver función que extrae el valor deseado del {@link Claims}.
   * @param <T>            tipo de dato devuelto.
   * @return valor del claim solicitado.
   */
  public <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = getAllClaims(token);
    return claimsResolver.apply(claims);
  }

  /**
   * Indica si el token se encuentra expirado.
   *
   * @param token JWT a evaluar.
   * @return {@code true} si la fecha de expiración es anterior a la fecha actual.
   */
  private boolean isTokenExpired(String token) {
    return getExpiration(token).before(new Date());
  }

  /**
   * Obtiene la fecha de expiración del token.
   *
   * @param token JWT origen.
   * @return objeto {@link Date} con la expiración.
   */
  private Date getExpiration(String token) {
    return getClaim(token, Claims::getExpiration);
  }

  /**
   * Convierte la clave secreta codificada en Base64 en una instancia de {@link Key}
   * apta para algoritmos HMAC-SHA.
   *
   * @return clave criptográfica para firmar/verificar JWT.
   */
  private Key getSigningKey() {
    byte[] keyBytes = Decoders.BASE64.decode(secretKey);
    return Keys.hmacShaKeyFor(keyBytes);
  }

  /**
   * Devuelve todos los claims contenidos en el token, validando la firma con la clave secreta.
   *
   * @param token JWT origen.
   * @return objeto {@link Claims} completo.
   */
  private Claims getAllClaims(String token) {
    return Jwts.parser()
        .setSigningKey(getSigningKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  /**
   * Valida que el token pertenezca al usuario indicado y no esté expirado.
   *
   * @param token       JWT a validar.
   * @param userDetails detalles del usuario autenticado.
   * @return {@code true} si el token es válido.
   */
  public boolean validateToken(String token, UserDetails userDetails) {
    final String username = getUserName(token);
    return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
  }

}