package br.com.maxclubcard.campanha.application.dto;

import br.com.maxclubcard.campanha.domain.Campanha;
import br.com.maxclubcard.cliente.domain.Cliente;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CampanhaDto {

  @JsonProperty(access = Access.READ_ONLY)
  @Schema(accessMode= AccessMode.READ_ONLY)
  private Long id;

  @NotNull
  private String nome;

  @JsonProperty(access = Access.READ_ONLY)
  @Schema(accessMode= AccessMode.READ_ONLY)
  private List<Cliente> cliente;

  public CampanhaDto(String nome) {
    this.nome = nome;
  }

  public static CampanhaDto map(Campanha campanha) {
    CampanhaDto campanhaDto = new CampanhaDto();
    campanhaDto.setId(campanha.getId());
    campanhaDto.setNome(campanha.getNome());
    campanhaDto.setCliente(campanha.getClientes());

    return campanhaDto;
  }
}
