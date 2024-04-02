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
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CartaoDto {

  @JsonProperty(access = Access.READ_ONLY)
  @Schema(accessMode= AccessMode.READ_ONLY)
  private Long id;

  @NotNull
  private Long idCliente;

  @NotNull
  @Pattern(regexp = "\\d+", message = "O número deve conter apenas caracteres numéricos")
  @Size(min = 16, max = 16, message = "O número deve ter 16 caracteres")
  private String numero;

  @NotNull
  private Tipo tipo;

  @NotNull
  private Bandeira bandeira;

  @NotNull
  @Pattern(regexp = "\\d{2}/\\d{2}/\\d{4}", message = "A data de expiração deve estar no formato dd/MM/yyyy")
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
    cartaoDto.setIdCliente(cartao.getCliente().getId());
    cartaoDto.setTipo(cartao.getTipo());
    cartaoDto.setBandeira(cartao.getBandeira());
    cartaoDto.setDataExpiracao(cartao.getDataExpiracao().toString());

    return cartaoDto;
  }
}
