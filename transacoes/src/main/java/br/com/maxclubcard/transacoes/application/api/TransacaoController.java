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
      description = "Gera uma transacao de pagamento",
      tags = {"Transacoes"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Transacao gerada com sucesso")})
  @PostMapping()
  public TransacaoDto gerar(
      @Parameter(description = "Dados da transacao a ser gerada, com informação do cliente(cpf), cartão e valor")
      @RequestBody @Valid TransacaoDto transacaoDto) {
    return transacaoService.gerar(transacaoDto);
  }
}
