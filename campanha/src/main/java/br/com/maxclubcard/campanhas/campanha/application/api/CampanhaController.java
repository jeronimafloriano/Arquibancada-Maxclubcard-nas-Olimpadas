package br.com.maxclubcard.campanhas.campanha.application.api;

import br.com.maxclubcard.campanhas.campanha.application.dto.CampanhaDto;
import br.com.maxclubcard.campanhas.campanha.application.service.CampanhaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tags(value = {@Tag(name = "Campanha", description = "Gerenciamento de campanhas")})
@RestController
@RequestMapping("/campanhas")
public class CampanhaController {

  private final CampanhaService campanhaService;

  public CampanhaController(CampanhaService campanhaService) {
    this.campanhaService = campanhaService;
  }

  @Operation(
      description = "Busca todas as campanhas cadastradas",
      tags = {"Campanha"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Campanhas retornadas com sucesso")})
  @GetMapping()
  public ResponseEntity<List<CampanhaDto>> buscar() {
    List<CampanhaDto> courses = campanhaService.buscar();
    return new ResponseEntity<>(courses, HttpStatus.OK);
  }

  @Operation(
      description = "Cadastra uma campanha",
      tags = {"Campanha"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Campanha cadastrada com sucesso")})
  @PostMapping()
  public CampanhaDto cadastrar(
      @Parameter(description = "Dados da campanha a ser cadastrada") @RequestBody @Valid CampanhaDto campanhaDto) {
    return campanhaService.cadastrar(campanhaDto);
  }
}
