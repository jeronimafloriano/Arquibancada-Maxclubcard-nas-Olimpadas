package br.com.maxclubcard.campanhas.cartao.domain;

import br.com.maxclubcard.campanhas.cliente.domain.Cliente;
import br.com.maxclubcard.campanhas.shared.exceptions.ValidationMessage;
import br.com.maxclubcard.campanhas.shared.exceptions.Validations;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@Getter
@Entity
@Table(name="cartao")
public class Cartao {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "cliente_id")
  private Cliente cliente;

  @Column(name = "numero")
  private String numero;

  @Column(name = "tipo")
  @Enumerated(EnumType.STRING)
  private Tipo tipo;

  @Column(name = "bandeira")
  @Enumerated(EnumType.STRING)
  private Bandeira bandeira;

  @Column(name = "data_expiracao", nullable = false)
  private LocalDate dataExpiracao;

  @Builder
  public Cartao(Cliente cliente, String numero, Tipo tipo,
      Bandeira bandeira, LocalDate dataExpiracao) {
    Validations.isNotNull(cliente, ValidationMessage.CLIENTE_OBRIGATORIO);
    Validations.isNotBlank(numero, ValidationMessage.NUMERO_CARTAO_OBRIGATORIO);
    Validations.isNotNull(tipo, ValidationMessage.TIPO_OBRIGATORIO);
    Validations.isNotNull(bandeira, ValidationMessage.BANDEIRA_OBRIGATORIA);
    Validations.isNotNull(dataExpiracao, ValidationMessage.DATA_EXPIRACAO_OBRIGATORIA);

    this.cliente = cliente;
    this.numero = numero;
    this.tipo = tipo;
    this.bandeira = bandeira;
    this.dataExpiracao = dataExpiracao;
  }

  protected Cartao() {
  }

  public static Cartao gerarCartao(List<String> dado, Cliente cliente) {
    String numeroCartao = dado.get(6).trim();
    LocalDate dataExp = LocalDate.parse(dado.get(7), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    Tipo tipo = Tipo.convert(dado.get(8));
    Bandeira bandeira = Bandeira.convert(dado.get(9));
    return Cartao.builder().numero(numeroCartao).dataExpiracao(dataExp).tipo(tipo)
        .bandeira(bandeira).cliente(cliente).build();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Cartao cartao = (Cartao) o;

    return new EqualsBuilder()
        .append(numero, cartao.numero)
        .append(tipo, cartao.tipo)
        .append(bandeira, cartao.bandeira)
        .append(dataExpiracao, cartao.dataExpiracao).isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .appendSuper(super.hashCode())
        .append(numero).append(numero)
        .append(tipo).append(tipo)
        .append(bandeira).append(bandeira)
        .append(dataExpiracao).append(dataExpiracao)
        .toHashCode();
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this).append("id", id)
        .append("nome", numero)
        .append("bandeira", bandeira.toString())
        .append("tipo", tipo.toString())
        .append("dataExpiracao", dataExpiracao.toString())
        .append("cliente", cliente)
        .toString();
  }
}
