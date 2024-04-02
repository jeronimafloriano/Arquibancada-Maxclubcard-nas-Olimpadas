package br.com.maxclubcard.campanhas.cartao.application.dto;

import br.com.maxclubcard.campanhas.cartao.domain.Bandeira;
import br.com.maxclubcard.campanhas.cartao.domain.Cartao;
import br.com.maxclubcard.campanhas.cartao.domain.Tipo;
import br.com.maxclubcard.campanhas.shared.exceptions.ValidationMessage;
import br.com.maxclubcard.campanhas.shared.exceptions.Validations;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CartaoDto {

  @JsonProperty(access = Access.READ_ONLY)
  @Schema(accessMode= AccessMode.READ_ONLY)
  private Long id;

  @JsonProperty(access = Access.WRITE_ONLY)
  @Schema(accessMode= AccessMode.WRITE_ONLY)
  @NotNull
  private Long idCliente;

  @NotNull
  private String numero;

  @NotNull
  private Tipo tipo;

  @NotNull
  private Bandeira bandeira;

  @NotNull
  private String dataExpiracao;


  public CartaoDto(Long idCliente, String numero, Tipo tipo,
      Bandeira bandeira, String dataExpiracao) {
    this.idCliente = idCliente;
    this.numero = numero;
    this.tipo = tipo;
    this.bandeira = bandeira;
    this.dataExpiracao = dataExpiracao;
  }

  public static CartaoDto map(Cartao cartao) {
    Validations.isNotNull(cartao, ValidationMessage.CARTAO_OBRIGATORIO);

    CartaoDto cartaoDto = new CartaoDto();
    cartaoDto.setId(cartao.getId());
    cartaoDto.setNumero(cartao.getNumero());
    cartaoDto.setTipo(cartao.getTipo());
    cartaoDto.setBandeira(cartao.getBandeira());
    cartaoDto.setDataExpiracao(cartao.getDataExpiracao().toString());

    return cartaoDto;
  }
}
