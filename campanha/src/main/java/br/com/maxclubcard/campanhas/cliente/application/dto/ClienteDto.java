package br.com.maxclubcard.campanhas.cliente.application.dto;

import br.com.maxclubcard.campanhas.cartao.application.dto.CartaoDto;
import br.com.maxclubcard.campanhas.cliente.domain.Cliente;
import br.com.maxclubcard.campanhas.cliente.domain.Sexo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

@NoArgsConstructor
@Data
public class ClienteDto {

  @JsonProperty(access = Access.READ_ONLY)
  @Schema(accessMode = AccessMode.READ_ONLY)
  private Long id;

  @NotNull
  private String nome;

  @CPF
  @NotNull
  private String cpf;

  @NotNull
  private String dataNascimento;

  @NotNull
  private Sexo sexo;

  @NotNull
  private String email;

  @NotNull
  private String celular;

  @JsonProperty(access = Access.READ_ONLY)
  @Schema(accessMode = AccessMode.READ_ONLY)
  private Set<CartaoDto> cartoes;

  public ClienteDto(String nome, String cpf, String dataNascimento,
      Sexo sexo, String email, String celular) {
    this.nome = nome;
    this.cpf = cpf;
    this.dataNascimento = dataNascimento;
    this.sexo = sexo;
    this.email = email;
    this.celular = celular;
  }

  public static ClienteDto map(Cliente cliente) {
    ClienteDto clienteDto = new ClienteDto();
    clienteDto.setId(cliente.getId());
    clienteDto.setNome(cliente.getNome());
    clienteDto.setCpf(cliente.getCpf());
    clienteDto.setDataNascimento(cliente.getDataNascimento().toString());
    clienteDto.setSexo(cliente.getSexo());
    clienteDto.setEmail(cliente.getEmail().getAddress());
    clienteDto.setCelular(cliente.getCelular());
    clienteDto.setCartoes(
        cliente.getCartoes().stream().map(CartaoDto::map).collect(Collectors.toSet()));

    return clienteDto;
  }
}
