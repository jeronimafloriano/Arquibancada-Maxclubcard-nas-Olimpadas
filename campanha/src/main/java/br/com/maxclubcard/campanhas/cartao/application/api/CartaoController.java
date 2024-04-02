package br.com.maxclubcard.campanhas.cartao.application.api;

import br.com.maxclubcard.campanhas.cartao.application.dto.CartaoDto;
import br.com.maxclubcard.campanhas.cartao.application.service.CartaoService;

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

@Tags(value = {@Tag(name = "Cartao", description = "Gerenciamento de cartoes")})
@RestController
@RequestMapping("/cartao")
public class CartaoController {

  private final CartaoService cartaoService;

  public CartaoController(CartaoService cartaoService) {
    this.cartaoService = cartaoService;
  }

  @Operation(
      description = "Cadastrar cartao para um cliente",
      tags = {"Cartao"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Cartao cadastrado com sucesso")})
  @PostMapping()
  public CartaoDto cadastrar(
      @Parameter(description = "Dados do cartão a ser cadastrado") @RequestBody @Valid CartaoDto cartaoDto) {
    return cartaoService.cadastrar(cartaoDto);
  }
}
