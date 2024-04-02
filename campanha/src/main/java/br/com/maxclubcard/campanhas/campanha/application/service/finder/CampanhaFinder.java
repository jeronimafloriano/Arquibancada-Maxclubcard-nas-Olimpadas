package br.com.maxclubcard.campanhas.campanha.application.service.finder;

import br.com.maxclubcard.campanhas.campanha.domain.Campanha;
import java.math.BigDecimal;
import java.util.List;

public interface CampanhaFinder {

  Campanha buscarPorId(Long idCampanha);

  List<Campanha> buscarPorClienteEValorCampanha(BigDecimal valor, String cpf);
}
