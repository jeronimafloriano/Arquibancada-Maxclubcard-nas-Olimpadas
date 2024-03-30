package br.com.maxclubcard.cartao.application.service;

import br.com.maxclubcard.cartao.domain.Cartao;
import br.com.maxclubcard.cliente.domain.Cliente;
import java.util.List;

public interface CartaoService {

  Cartao converterCartao(List<String> dado, Cliente cliente);

  void salvar(List<Cartao> cartoes);
}
