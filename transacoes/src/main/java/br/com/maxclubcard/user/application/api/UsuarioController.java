package br.com.maxclubcard.user.application.api;

import br.com.maxclubcard.config.auth.dto.LoginReqDto;
import br.com.maxclubcard.config.auth.dto.LoginResDto;
import br.com.maxclubcard.user.application.dto.UsuarioDto;
import br.com.maxclubcard.user.application.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Usuario", description = "Registro e login de usuário para processamento de transações")
@RestController
@RequestMapping("/usuario")
public class UsuarioController {

  private final UsuarioService usuarioService;

  public UsuarioController(UsuarioService usuarioService) {
    this.usuarioService = usuarioService;
  }

  @Operation(
      description = "Registra um usuário",
      tags = {"Usuario"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "User created")})
  @PostMapping()
  public UsuarioDto register(
      @Parameter(description = "Informações de username e senha do usuário") @RequestBody @Valid
          UsuarioDto usuarioDto) {
    return usuarioService.register(usuarioDto);
  }

  @Operation(
      description = "Realiza o login de um usuário retornando o token gerado",
      tags = {"Usuario"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Usuário logado")})
  @PostMapping("/login")
  public ResponseEntity<LoginResDto> login(
      @Parameter(description = "Username e senha para realização de login") @Valid @RequestBody LoginReqDto body) {
    return new ResponseEntity<>(usuarioService.login(body), HttpStatus.OK);
  }
}
