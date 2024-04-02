package br.com.maxclubcard.config;

import javax.annotation.PostConstruct;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
public class RabbitConfig {

  @Value("${spring.rabbitmq.queue}")
  private String name;

  @Bean
  public Queue queue() {
    return new Queue(name, true);
  }

  private AmqpAdmin amqpAdmin;

  public RabbitConfig(AmqpAdmin amqpAdmin) {
    this.amqpAdmin = amqpAdmin;
  }

  @PostConstruct
  public void createQueues() {
    amqpAdmin.declareQueue(queue());
  }
}
