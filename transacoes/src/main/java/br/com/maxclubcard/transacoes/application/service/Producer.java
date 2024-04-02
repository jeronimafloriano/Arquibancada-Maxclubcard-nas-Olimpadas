package br.com.maxclubcard.transacoes.application.service;

import br.com.maxclubcard.transacoes.domain.Transacao;

public interface Producer {

  void send(Transacao transacao);

}
