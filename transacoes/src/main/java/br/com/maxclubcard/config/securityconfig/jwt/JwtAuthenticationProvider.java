package br.com.maxclubcard.config.securityconfig.jwt;

import br.com.maxclubcard.config.auth.JwtService;
import br.com.maxclubcard.user.domain.model.Usuario;
import br.com.maxclubcard.user.domain.repository.UsuarioRepository;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.ProviderNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {

  private final JwtService jwtService;
  private final UsuarioRepository usuarioRepository;

  private static final String USUARIO_NAO_ENCONTRADO = "Usuário não encontrado.";
  private static final String TOKEN_INVALIDO = "Token JWT inválido.";

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    String token = (String) authentication.getCredentials();

    if (!jwtService.isTokenValid(token)) {
      throw new InsufficientAuthenticationException(TOKEN_INVALIDO);
    }

    String strId = jwtService.extractId(token);
    Usuario usuario = usuarioRepository
        .findById(Long.valueOf(strId))
        .orElseThrow(() -> new ProviderNotFoundException(USUARIO_NAO_ENCONTRADO));

    return new JwtAuthenticationToken(token, usuario, new ArrayList<>());
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return authentication.equals(JwtAuthenticationToken.class);
  }
}
