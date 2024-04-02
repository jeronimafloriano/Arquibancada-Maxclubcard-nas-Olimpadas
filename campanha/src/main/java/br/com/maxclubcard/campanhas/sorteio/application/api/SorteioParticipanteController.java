package br.com.maxclubcard.campanhas.sorteio.application.api;

import br.com.maxclubcard.campanhas.sorteio.application.dto.SorteioParticipanteDto;
import br.com.maxclubcard.campanhas.sorteio.application.service.SorteioParticipanteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tags(value = {@Tag(name = "Sorteio", description = "Gerenciamento de participação de sorteios")})
@RestController
@RequestMapping("/sorteio")
public class SorteioParticipanteController {

  private final SorteioParticipanteService sorteioParticipanteService;

  public SorteioParticipanteController(SorteioParticipanteService sorteioParticipanteService) {
    this.sorteioParticipanteService = sorteioParticipanteService;
  }

  @Operation(
      description = "Realiza um sorteio com base nos participantes cadastrados em uma campanha",
      tags = {"Sorteio"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Sorteio realizado com sucesso")})
  @GetMapping("/{id}/sorteio")
  public ResponseEntity<SorteioParticipanteDto> realizarSorteio(
      @Parameter(description = "Id da campanha a ser sorteada")
      @PathVariable Long id) {
    SorteioParticipanteDto sorteio = sorteioParticipanteService.realizarSorteio(id);
    return new ResponseEntity<>(sorteio, HttpStatus.OK);
  }
}
