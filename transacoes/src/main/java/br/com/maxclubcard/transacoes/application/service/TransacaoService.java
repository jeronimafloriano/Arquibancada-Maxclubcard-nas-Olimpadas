package br.com.maxclubcard.transacoes.application.service;

import br.com.maxclubcard.transacoes.application.dto.TransacaoDto;

public interface TransacaoService {

  TransacaoDto gerar(TransacaoDto transacaoDto);
}
