package br.com.maxclubcard.config.leitor;

import java.util.Collection;
import java.util.List;

public interface LeitorArquivo {

  Collection<List<String>> lerArquivo(String caminhoArquivo);
}
