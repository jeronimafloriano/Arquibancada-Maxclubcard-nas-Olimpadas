package br.com.maxclubcard.campanhas.cliente.application.api;

import br.com.maxclubcard.campanhas.cliente.application.dto.ClienteDto;
import br.com.maxclubcard.campanhas.cliente.application.service.ClienteService;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tags(value = {@Tag(name = "Cliente", description = "Gerenciamento de clientes")})
@RestController
@RequestMapping("/cliente")
public class ClienteController {

  private final ClienteService clienteService;

  public ClienteController(ClienteService clienteService) {
    this.clienteService = clienteService;
  }

  @Operation(
      description = "Cadastrar cliente em uma campanha",
      tags = {"Cliente"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Cliente cadastrado com sucesso")})
  @PostMapping("/campanha/{id}")
  public ClienteDto cadastrar(
      @Parameter(description = "Id da campanha") @PathVariable Long id,
      @Parameter(description = "Dados da campanha a ser cadastrada") @RequestBody @Valid ClienteDto clienteDto) {
    return clienteService.cadastrar(id, clienteDto);
  }

  @Operation(
      description = "Adiciona clientes à uma campanha via arquivo",
      tags = {"Cliente"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Campanhas adicionadas com sucesso")})
  @PostMapping("/campanha/{id}/arquivo")
  public ResponseEntity<List<ClienteDto>> adicionar(@Parameter(description = "Id da campanha") @PathVariable Long id) {
    List<ClienteDto> clientes = clienteService.adicionarViaArquivo(id);
    return new ResponseEntity<>(clientes, HttpStatus.OK);
  }
}
