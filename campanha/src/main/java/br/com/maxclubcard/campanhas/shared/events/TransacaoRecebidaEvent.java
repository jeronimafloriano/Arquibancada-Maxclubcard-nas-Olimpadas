package br.com.maxclubcard.campanhas.shared.events;

import org.springframework.context.ApplicationEvent;

public class TransacaoRecebidaEvent extends ApplicationEvent {

  public TransacaoRecebidaEvent(String message) {
    super(message);
  }

  public String getMessage() {
    return (String) getSource();
  }

}
