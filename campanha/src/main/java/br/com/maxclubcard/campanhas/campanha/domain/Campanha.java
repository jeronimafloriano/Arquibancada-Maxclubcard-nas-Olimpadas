package br.com.maxclubcard.campanhas.campanha.domain;

import br.com.maxclubcard.campanhas.cliente.domain.Cliente;
import br.com.maxclubcard.campanhas.shared.exceptions.ValidationMessage;
import br.com.maxclubcard.campanhas.shared.exceptions.Validations;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.math.BigDecimal;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.Getter;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@Getter
@Entity
@Table(name="campanha")
public class Campanha {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "nome")
  private String nome;

  @Column(name = "valor_minimo")
  private BigDecimal valorMinimo;

  @ManyToMany
  @JoinTable(
      name = "cliente_campanha",
      joinColumns = @JoinColumn(name = "campanha_id"),
      inverseJoinColumns = @JoinColumn(name = "cliente_id"))
  @JsonIgnore
  private Set<Cliente> clientes;

  public Campanha(String nome, BigDecimal valorMinimo) {
    Validations.isNotBlank(nome, ValidationMessage.NOME_OBRIGATORIO);
    Validations.isNotNull(valorMinimo, ValidationMessage.VALOR_MINIMO_OBRIGATORIO);

    this.nome = nome;
    this.valorMinimo = valorMinimo;
  }

  protected Campanha() {
  }

  public void vincularClientes(Set<Cliente> clientes) {
    this.clientes.addAll(clientes);
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
