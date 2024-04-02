package br.com.maxclubcard.config.securityconfig.jwt;

import br.com.maxclubcard.config.securityconfig.AbstractAuthenticationFilter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationFilter extends AbstractAuthenticationFilter {

  public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
    super(authenticationManager);

    setRequestMatcher(
      request -> !request.getRequestURI().startsWith("/auth")
    );
  }

  @Override
  public AuthenticationConverter getAuthenticationConverter() {
    return request -> {
      String authorizationHeader = request.getHeader("Authorization");
      if (authorizationHeader != null) {
        return new JwtAuthenticationToken(authorizationHeader);
      }
      return null;
    };
  }

}

