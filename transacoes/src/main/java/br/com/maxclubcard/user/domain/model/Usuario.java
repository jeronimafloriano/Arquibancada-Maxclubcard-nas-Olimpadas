package br.com.maxclubcard.user.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@Getter
@Entity
@Table(name = "usuario")
public class Usuario {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "username")
  private String username;

  @Column(name = "password", length = 255)
  private String password;

  public Usuario(Long id, String username, String password) {
    this.id = id;
    this.username = username;
    this.password = password;
  }

  public Usuario(String username, String password) {
    this.id = id;
    this.username = username;
    this.password = password;
  }

  protected Usuario() {
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Usuario usuario = (Usuario) o;

    if(usuario.getId() != null && usuario.getId().equals(getId())) {
      return true;
    }

    return new EqualsBuilder()
        .append(username, usuario.username).isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .append(username)
        .toHashCode();
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("id", id)
        .append("username", username)
        .toString();
  }
}
