package br.com.maxclubcard.transacoes.application.dto;

import br.com.maxclubcard.transacoes.domain.Tipo;
import br.com.maxclubcard.transacoes.domain.Transacao;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import java.math.BigDecimal;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class TransacaoDto {

  @JsonProperty(access = Access.READ_ONLY)
  @Schema(accessMode= AccessMode.READ_ONLY)
  private Long id;

  @NotNull
  private String cpf;

  @NotNull
  private BigDecimal valor;

  @NotNull
  private String numeroCartao;

  @NotNull
  private Tipo tipo;

  public TransacaoDto(String cpf, BigDecimal valor, String numeroCartao,
      Tipo tipo) {
    this.cpf = cpf;
    this.valor = valor;
    this.numeroCartao = numeroCartao;
    this.tipo = tipo;
  }

  public static TransacaoDto map(Transacao transacao) {
    TransacaoDto transacaoDto = new TransacaoDto();
    transacaoDto.setId(transacao.getId());
    transacaoDto.setCpf(transacao.getCpf());
    transacaoDto.setNumeroCartao(transacao.getNumeroCartao());
    transacaoDto.setValor(transacao.getValor());
    transacaoDto.setTipo(transacao.getTipo());

    return transacaoDto;
  }
}
