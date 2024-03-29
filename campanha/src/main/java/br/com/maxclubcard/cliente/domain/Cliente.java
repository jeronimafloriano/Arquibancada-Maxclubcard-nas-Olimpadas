package br.com.maxclubcard.cliente.domain;

import br.com.maxclubcard.campanha.domain.Campanha;
import br.com.maxclubcard.cartao.domain.Cartao;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.validator.constraints.br.CPF;

@Getter
@Entity
@Table(name="cliente")
public class Cliente {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "nome")
  private String nome;

  @CPF
  @Column(name = "cpf")
  private String cpf;

  @Column(name = "data_nascimento")
  private LocalDate dataNascimento;

  @Enumerated(EnumType.STRING)
  @Column(name = "sexo")
  private Sexo sexo;

  @Embedded
  private Email email;

  @Column(name = "celular")
  private String celular;

  @ManyToMany(mappedBy = "clientes")
  private List<Campanha> campanhas;

  @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
  private Set<Cartao> cartoes;

  public Cliente(String nome, String cpf, LocalDate dataNascimento, Sexo sexo, Email email,
      String celular) {

    this.nome = nome;
    this.cpf = cpf;
    this.dataNascimento = dataNascimento;
    this.sexo = sexo;
    this.email = email;
    this.celular = celular;
  }

  protected Cliente() {
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Cliente cliente = (Cliente) o;

    if (cliente.getId() != null && cliente.getId().equals(getId())) {
      return true;
    }

    return new EqualsBuilder()
        .append(cpf, cliente.cpf).isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37).appendSuper(super.hashCode()).append(cpf).append(cpf)
        .toHashCode();
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this).append("id", id)
        .append("nome", nome)
        .append("cpf", cpf)
        .append("dataNascimento", dataNascimento)
        .append("sexo", sexo)
        .append("email", email)
        .append("celular", celular)
        .toString();
  }

}
