package br.com.maxclubcard.transacoes.application.service.impl;

import br.com.maxclubcard.transacoes.application.service.Producer;
import br.com.maxclubcard.transacoes.domain.Transacao;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class RabbitProducer implements Producer {

  private RabbitTemplate rabbitTemplate;
  private Queue queue;

  public RabbitProducer(RabbitTemplate rabbitTemplate, Queue queue) {
    this.rabbitTemplate = rabbitTemplate;
    this.queue = queue;
  }

  public void send(Transacao transacao) {
    rabbitTemplate.convertAndSend(queue.getName(), transacao.toString());
  }
}
