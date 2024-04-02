package br.com.maxclubcard.campanhas.cartao.domain;

import java.text.Normalizer;
import java.util.Locale;

public enum Tipo {
  DEBITO,
  CREDITO;

  public static Tipo convert(String tipo) {
    String tipoSemAcento = Normalizer.normalize(tipo, Normalizer.Form.NFD)
        .replaceAll("\\p{M}", "");
    return Tipo.valueOf(tipoSemAcento.toUpperCase(Locale.ROOT));
  }
}
