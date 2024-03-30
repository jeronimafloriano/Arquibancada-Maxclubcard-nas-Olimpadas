package br.com.maxclubcard.cartao.domain;

import java.util.Locale;

public enum Bandeira {
  MAXCLUBCARD;

  public static Bandeira convert(String bandeira) {
    return Bandeira.valueOf(bandeira.toUpperCase(Locale.ROOT));
  }
}
