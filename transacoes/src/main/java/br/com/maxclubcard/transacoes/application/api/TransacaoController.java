package br.com.maxclubcard.transacoes.application.api;

import br.com.maxclubcard.transacoes.application.dto.TransacaoDto;
import br.com.maxclubcard.transacoes.application.service.TransacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tags(value = {@Tag(name = "Transacoes", description = "Gerenciamento de transacoes")})
@RestController
@RequestMapping("/transacao")
public class TransacaoController {

  private final TransacaoService transacaoService;

  public TransacaoController(TransacaoService transacaoService) {
    this.transacaoService = transacaoService;
  }

  @Operation(
      description = "Retorna a porta que foi indicada para rodar a aplicação",
      tags = {"Transacoes"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Porta encontrada com sucesso")})
  @GetMapping("/porta")
  public String retornaPorta(@Value("${local.server.port}") String porta) {
    return String.format("Requisição respondida pela instância executando na porta %s", porta);
  }

  @Operation(
      description = "Gera uma transacao de pagamento",
      tags = {"Transacoes"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Transacao gerada com sucesso")})
  @PostMapping()
  public TransacaoDto gerar(
      @Parameter(description = "Dados da transacao a ser gerada") @RequestBody @Valid TransacaoDto transacaoDto) {
    return transacaoService.gerar(transacaoDto);
  }

}
