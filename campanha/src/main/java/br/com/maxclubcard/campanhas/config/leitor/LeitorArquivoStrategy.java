package br.com.maxclubcard.campanhas.config.leitor;

import java.util.Collection;
import java.util.List;

public class LeitorArquivoStrategy {

  private LeitorArquivo leitorArquivo;

  public LeitorArquivoStrategy(LeitorArquivo leitorArquivo) {
    this.leitorArquivo = leitorArquivo;
  }

  public void setLeitorArquivo(LeitorArquivo leitorArquivo) {
    this.leitorArquivo = leitorArquivo;
  }

  public Collection<List<String>> lerArquivo(String caminhoArquivo) {
    return this.leitorArquivo.lerArquivo(caminhoArquivo);
  }
}
