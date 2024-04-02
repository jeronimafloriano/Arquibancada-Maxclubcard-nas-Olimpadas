package br.com.maxclubcard.campanhas.campanha.application.dto;

import br.com.maxclubcard.campanhas.campanha.domain.Campanha;
import br.com.maxclubcard.campanhas.cliente.application.dto.ClienteDto;
import br.com.maxclubcard.campanhas.cliente.domain.Cliente;
import br.com.maxclubcard.campanhas.shared.exceptions.ValidationMessage;
import br.com.maxclubcard.campanhas.shared.exceptions.Validations;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CampanhaDto {

  @JsonProperty(access = Access.READ_ONLY)
  @Schema(accessMode= AccessMode.READ_ONLY)
  private Long id;

  @NotNull
  private String nome;

  @NotNull
  private BigDecimal valorMinimo;

  @JsonProperty(access = Access.READ_ONLY)
  @Schema(accessMode= AccessMode.READ_ONLY)
  private Set<ClienteDto> cliente;

  public CampanhaDto(String nome, BigDecimal valorMinimo) {
    this.nome = nome;
    this.valorMinimo = valorMinimo;
  }

  public static CampanhaDto map(Campanha campanha) {
    Validations.isNotNull(campanha, ValidationMessage.CAMPANHA_OBRIGATORIA);

    CampanhaDto campanhaDto = new CampanhaDto();
    campanhaDto.setId(campanha.getId());
    campanhaDto.setNome(campanha.getNome());
    campanhaDto.setValorMinimo(campanha.getValorMinimo());

    if(campanha.getClientes()!= null) {
      campanhaDto.setCliente(campanha.getClientes()
          .stream().map(ClienteDto::map).collect(Collectors.toSet()));
    }
    return campanhaDto;
  }
}
