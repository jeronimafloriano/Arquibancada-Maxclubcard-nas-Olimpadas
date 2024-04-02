package br.com.maxclubcard.campanhas.cliente.domain;

import java.util.Locale;

public enum Sexo {
  FEMININO,
  MASCULINO;

  public static Sexo convert(String sexo) {
    return Sexo.valueOf(sexo.toUpperCase(Locale.ROOT));
  }
}
