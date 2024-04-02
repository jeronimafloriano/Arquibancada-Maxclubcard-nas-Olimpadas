package br.com.maxclubcard.campanhas.sorteio.domain;

import br.com.maxclubcard.campanhas.campanha.domain.Campanha;
import br.com.maxclubcard.campanhas.cliente.domain.Cliente;
import br.com.maxclubcard.campanhas.shared.exceptions.ValidationMessage;
import br.com.maxclubcard.campanhas.shared.exceptions.Validations;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@Getter
@Entity
@Table(name="sorteio_participante")
public class SorteioParticipante {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "numero_da_sorte")
  private Long numeroDaSorte;

  @ManyToOne
  @JoinColumn(name = "campanha_id")
  private Campanha campanha;

  @ManyToOne
  @JoinColumn(name = "cliente_id")
  private Cliente cliente;

  public SorteioParticipante(Long numeroDaSorte, Campanha campanha,
      Cliente cliente) {
    Validations.isNotNull(numeroDaSorte, ValidationMessage.NUMERO_DA_SORTE_OBRIGATORIO);
    Validations.isNotNull(campanha, ValidationMessage.CAMPANHA_OBRIGATORIA);
    Validations.isNotNull(cliente, ValidationMessage.CLIENTE_OBRIGATORIO);

    this.numeroDaSorte = numeroDaSorte;
    this.campanha = campanha;
    this.cliente = cliente;
  }

  protected SorteioParticipante() {
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    SorteioParticipante sorteioParticipante = (SorteioParticipante) o;

    return new EqualsBuilder()
        .append(id, sorteioParticipante.id).isEquals();
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
        .append("numeroDaSorte", numeroDaSorte)
        .append("campanha", campanha)
        .append("cliente", cliente)
        .toString();
  }
}
