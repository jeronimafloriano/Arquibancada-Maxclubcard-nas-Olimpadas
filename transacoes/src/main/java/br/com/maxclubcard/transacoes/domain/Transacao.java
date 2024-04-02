package br.com.maxclubcard.transacoes.domain;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@Getter
@Entity
@Table(name="transacao")
public class Transacao {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "cpf")
  private String cpf;

  @Column(name = "valor")
  private BigDecimal valor;

  @Column(name = "numero_cartao")
  private String numeroCartao;

  @Enumerated(EnumType.STRING)
  @Column(name = "tipo")
  private Tipo tipo;

  @Builder
  public Transacao(String cpf, BigDecimal valor, String numeroCartao,
      Tipo tipo) {
    this.cpf = cpf;
    this.valor = valor;
    this.numeroCartao = numeroCartao;
    this.tipo = tipo;
  }

  protected Transacao() {
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Transacao transacao = (Transacao) o;

    return new EqualsBuilder()
        .append(id, transacao.id).isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .append(id).append(id)
        .toHashCode();
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("id", id)
        .append("cpf", cpf)
        .append("valor", valor)
        .append("numero do cartao", numeroCartao)
        .append("tipo", tipo)
        .toString();
  }
}
