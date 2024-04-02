package br.com.maxclubcard.campanhas.cliente.domain;

import br.com.maxclubcard.campanhas.campanha.domain.Campanha;
import br.com.maxclubcard.campanhas.cartao.domain.Cartao;
import br.com.maxclubcard.campanhas.shared.exceptions.ValidationMessage;
import br.com.maxclubcard.campanhas.shared.exceptions.Validations;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
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
import lombok.Builder;
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
  @JsonIgnore
  private List<Campanha> campanhas = new ArrayList<>();

  @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
  @JsonIgnore
  private Set<Cartao> cartoes = new HashSet<>();

  @Builder
  public Cliente(String nome, String cpf, LocalDate dataNascimento, Sexo sexo, Email email,
      String celular) {
    Validations.isNotBlank(nome, ValidationMessage.NOME_OBRIGATORIO);
    Validations.isNotBlank(cpf, ValidationMessage.CPF_OBRIGATORIO);
    Validations.isNotNull(dataNascimento, ValidationMessage.DATA_NASCIMENTO_OBRIGATORIA);
    Validations.isNotNull(sexo, ValidationMessage.SEXO_OBRIGATORIO);
    Validations.isNotNull(email, ValidationMessage.EMAIL_OBRIGATORIO);
    Validations.isNotNull(celular, ValidationMessage.CELULAR_OBRIGATORIO);

    this.nome = nome;
    this.cpf = cpf;
    this.dataNascimento = dataNascimento;
    this.sexo = sexo;
    this.email = email;
    this.celular = celular;
  }

  protected Cliente() {
  }

  public void vincularCartao(Cartao cartao) {
    this.cartoes.add(cartao);
  }

  public static Cliente gerarCliente(List<String> dado, Campanha campanha) {
    String nome = dado.get(0);
    String cpf = dado.get(1);
    LocalDate nascimento = LocalDate.parse(dado.get(2), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    Sexo sexo = Sexo.convert(dado.get(3));
    Email email = Email.of(dado.get(4));
    String celular = dado.get(5);

    Cliente cliente = Cliente.builder().nome(nome).cpf(cpf)
        .dataNascimento(nascimento).sexo(sexo).email(email)
        .celular(celular).build();

    cliente.campanhas.add(campanha);
    return cliente;
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
    return new HashCodeBuilder(17, 37)
        .append(cpf).append(cpf)
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
