package br.com.maxclubcard.campanha.domain;

import br.com.maxclubcard.cliente.domain.Cliente;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import lombok.Getter;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@Getter
@Entity
public class Campanha {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "nome")
  private String nome;

  @ManyToMany
  @JoinTable(
      name = "cliente_campanha",
      joinColumns = @JoinColumn(name = "campanha_id"),
      inverseJoinColumns = @JoinColumn(name = "cliente_id"))
  private List<Cliente> clientes;

  public Campanha(String nome) {
    this.nome = nome;
  }

  protected Campanha() {
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Campanha campanha = (Campanha) o;

    return new EqualsBuilder()
        .append(id, campanha.id).isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .appendSuper(super.hashCode()).append(id).append(id)
        .toHashCode();
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("id", id)
        .append("nome", nome)
        .toString();
  }
}