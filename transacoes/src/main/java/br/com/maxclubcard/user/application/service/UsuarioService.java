package br.com.maxclubcard.user.application.service;

import br.com.maxclubcard.config.auth.dto.LoginReqDto;
import br.com.maxclubcard.config.auth.dto.LoginResDto;
import br.com.maxclubcard.user.application.dto.UsuarioDto;

public interface UsuarioService {

  UsuarioDto register(UsuarioDto usuarioDto);

  LoginResDto login(LoginReqDto body);
}
