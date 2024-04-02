package br.com.maxclubcard.transacoes.application.service.impl;

import br.com.maxclubcard.transacoes.application.dto.TransacaoDto;
import br.com.maxclubcard.transacoes.application.service.Producer;
import br.com.maxclubcard.transacoes.application.service.TransacaoService;
import br.com.maxclubcard.transacoes.domain.Transacao;
import br.com.maxclubcard.transacoes.domain.repository.TransacaoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransacaoServiceImpl implements TransacaoService {

  private final TransacaoRepository transacaoRepository;
  private Producer producer;

  public TransacaoServiceImpl(TransacaoRepository transacaoRepository,
      Producer producer) {
    this.transacaoRepository = transacaoRepository;
    this.producer = producer;
  }

  @Transactional
  @Override
  public TransacaoDto gerar(TransacaoDto transacaoDto) {
    Transacao transacao = Transacao.builder()
        .cpf(transacaoDto.getCpf())
        .numeroCartao(transacaoDto.getNumeroCartao())
        .tipo(transacaoDto.getTipo())
        .valor(transacaoDto.getValor())
        .build();

    transacaoRepository.save(transacao);
    producer.send(transacao);

    return TransacaoDto.map(transacao);
  }
}
