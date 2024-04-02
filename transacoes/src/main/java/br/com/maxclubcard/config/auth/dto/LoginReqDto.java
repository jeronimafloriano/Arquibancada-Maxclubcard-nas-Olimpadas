package br.com.maxclubcard.config.auth.dto;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginReqDto {
  @NotNull
  private String username;

  @NotNull
  private String password;
}
