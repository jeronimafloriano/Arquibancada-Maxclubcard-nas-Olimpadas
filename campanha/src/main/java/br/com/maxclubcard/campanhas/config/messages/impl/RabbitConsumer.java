package br.com.maxclubcard.campanhas.config.messages.impl;

import br.com.maxclubcard.campanhas.config.messages.MessageConsumer;
import br.com.maxclubcard.campanhas.events.TransacaoRecebidaEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class RabbitConsumer implements MessageConsumer {

  private static final Logger LOG = LoggerFactory.getLogger(RabbitConsumer.class);

  private final ApplicationEventPublisher eventPublisher;

  public RabbitConsumer(ApplicationEventPublisher eventPublisher) {
    this.eventPublisher = eventPublisher;
  }

  @RabbitListener(queues = "${spring.rabbitmq.queue}", concurrency = "3-7")
  public void listen(String message) {
    LOG.info("Mensagem Recebida: {}", message);
    eventPublisher.publishEvent(new TransacaoRecebidaEvent(message));
  }

}
