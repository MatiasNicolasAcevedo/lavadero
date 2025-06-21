package tech.munidigital.lavadero.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;

/**
 * Entidad que representa a un usuario autenticable del sistema.
 * <p>
 * Implementa {@link UserDetails} para integrarse con Spring Security.
 * No se manejan roles por ahora, por lo que {@code getAuthorities()} devuelve
 * una lista vacía.
 */
@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "usuarios")
public class User implements UserDetails {

  /**
   * Identificador primario autoincremental.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private String firstName;
  private String lastName;

  /**
   * Se usa como nombre de usuario en el proceso de login.
   */
  private String email;

  /**
   * Contraseña codificada (BCrypt).
   */
  private String password;

  private String phone;

  /**
   * No se definen roles/authorities en esta versión.
   */
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of();
  }

  @Override
  public String getUsername() {
    return this.email;
  }

  /**
   * Todas las banderas de expiración/bloqueo se dejan habilitadas por defecto.
   */
  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

}