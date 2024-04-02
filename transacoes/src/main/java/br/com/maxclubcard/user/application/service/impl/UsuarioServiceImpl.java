package br.com.maxclubcard.user.application.service.impl;

import br.com.maxclubcard.config.auth.JwtService;
import br.com.maxclubcard.config.auth.dto.LoginReqDto;
import br.com.maxclubcard.config.auth.dto.LoginResDto;
import br.com.maxclubcard.user.application.dto.UsuarioDto;
import br.com.maxclubcard.user.application.service.UsuarioService;
import br.com.maxclubcard.user.domain.repository.UsuarioRepository;
import br.com.maxclubcard.user.domain.model.Usuario;
import br.com.maxclubcard.user.exception.DuplicatedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

  private final UsuarioRepository usuarioRepository;
  private final JwtService jwtService;
  private final PasswordEncoder passwordEncoder;

  public UsuarioServiceImpl(UsuarioRepository usuarioRepository, JwtService jwtService,
      PasswordEncoder passwordEncoder) {
    this.usuarioRepository = usuarioRepository;
    this.jwtService = jwtService;
    this.passwordEncoder = passwordEncoder;
  }

  @Transactional
  @Override
  public UsuarioDto register(UsuarioDto usuarioDto) {
    validateDuplicityUsername(usuarioDto.getUsername());

    String encode = passwordEncoder.encode(usuarioDto.getPassword());
    Usuario usuario = new Usuario(usuarioDto.getUsername(), encode);
    usuarioRepository.save(usuario);
    return UsuarioDto.map(usuario);
  }

  public LoginResDto login(LoginReqDto body) {
    Usuario usuario = usuarioRepository.findByUsername(body.getUsername())
        .orElseThrow(() -> new UsernameNotFoundException(body.getUsername()));

    if (!passwordEncoder.matches(body.getPassword(), usuario.getPassword())) {
      throw new AuthenticationCredentialsNotFoundException(body.getUsername());
    }
    String token = jwtService.generateToken(usuario.getId(),
        usuario.getUsername());
    return new LoginResDto(token);
  }

  private void validateDuplicityUsername(String username) {
    usuarioRepository.findByUsername(username)
        .ifPresent(user -> {
          throw new DuplicatedException(username);
        });
  }
}
