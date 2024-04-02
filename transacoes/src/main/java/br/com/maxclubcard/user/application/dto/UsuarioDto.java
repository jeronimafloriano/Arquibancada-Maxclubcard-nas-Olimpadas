package br.com.maxclubcard.user.application.dto;

import br.com.maxclubcard.user.domain.model.Usuario;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UsuarioDto {

  @JsonProperty(access = Access.READ_ONLY)
  @Schema(accessMode= AccessMode.READ_ONLY)
  private Long id;

  @JsonProperty(access = Access.WRITE_ONLY)
  @Size(max = 20, message = "The name must have a maximum of 20 characters.")
  @NotNull
  private String username;

  @JsonProperty(access = Access.WRITE_ONLY)
  @Size(max = 20, message = "The password must have a maximum of 20 characters.")
  @NotNull
  private String password;


  public UsuarioDto(String username, String password) {
    this.username = username;
    this.password = password;
  }

  public static UsuarioDto map(Usuario usuario) {
    UsuarioDto usuarioDto = new UsuarioDto();
    usuarioDto.setId(usuario.getId());
    usuarioDto.setUsername(usuario.getUsername());

    return usuarioDto;
  }
}
