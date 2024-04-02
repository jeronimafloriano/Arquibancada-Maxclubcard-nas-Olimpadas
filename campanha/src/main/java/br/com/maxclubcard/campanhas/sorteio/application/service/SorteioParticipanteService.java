package br.com.maxclubcard.campanhas.sorteio.application.service;

import br.com.maxclubcard.campanhas.sorteio.application.dto.SorteioParticipanteDto;

public interface SorteioParticipanteService {

  SorteioParticipanteDto realizarSorteio(Long campanhaId);
}
