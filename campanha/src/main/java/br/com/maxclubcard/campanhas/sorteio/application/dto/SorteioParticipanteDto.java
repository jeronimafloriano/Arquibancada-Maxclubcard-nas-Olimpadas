package br.com.maxclubcard.campanhas.sorteio.application.dto;

import br.com.maxclubcard.campanhas.campanha.application.dto.CampanhaDto;
import br.com.maxclubcard.campanhas.sorteio.domain.SorteioParticipante;
import br.com.maxclubcard.campanhas.cliente.application.dto.ClienteDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SorteioParticipanteDto {

  @JsonProperty(access = Access.READ_ONLY)
  @Schema(accessMode= AccessMode.READ_ONLY)
  private Long id;

  @NotNull
  private Long numeroDaSorte;

  @NotNull
  private Long idCampanha;

  @NotNull
  private String nomeCampanha;

  @NotNull
  private ClienteDto cliente;

  public SorteioParticipanteDto(Long numeroDaSorte,
      Long idCampanha, String nomeCampanha, ClienteDto cliente) {
    this.numeroDaSorte = numeroDaSorte;
    this.idCampanha = idCampanha;
    this.nomeCampanha = nomeCampanha;
    this.cliente = cliente;
  }

  public static SorteioParticipanteDto map(SorteioParticipante sorteioParticipante) {
    SorteioParticipanteDto sorteioParticipanteDto = new SorteioParticipanteDto();
    sorteioParticipanteDto.setId(sorteioParticipante.getId());
    sorteioParticipanteDto.setNumeroDaSorte(sorteioParticipante.getNumeroDaSorte());
    sorteioParticipanteDto.setCliente(ClienteDto.map(sorteioParticipante.getCliente()));
    sorteioParticipanteDto.setIdCampanha(sorteioParticipante.getCampanha().getId());
    sorteioParticipanteDto.setNomeCampanha(sorteioParticipante.getCampanha().getNome());

    return sorteioParticipanteDto;
  }
}
