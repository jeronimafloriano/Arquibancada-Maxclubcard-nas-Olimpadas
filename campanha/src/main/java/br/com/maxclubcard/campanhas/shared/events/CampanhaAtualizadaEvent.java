package br.com.maxclubcard.campanhas.shared.events;

import br.com.maxclubcard.campanhas.campanha.domain.Campanha;

import org.springframework.context.ApplicationEvent;

public class CampanhaAtualizadaEvent extends ApplicationEvent {

  public CampanhaAtualizadaEvent(Campanha campanha) {
    super(campanha);
  }

  public Campanha getCampanha() {
    return (Campanha) getSource();
  }

}
