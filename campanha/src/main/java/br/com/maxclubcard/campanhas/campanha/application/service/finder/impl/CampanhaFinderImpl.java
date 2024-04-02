package br.com.maxclubcard.campanhas.campanha.application.service.finder.impl;

import br.com.maxclubcard.campanhas.campanha.application.service.finder.CampanhaFinder;
import br.com.maxclubcard.campanhas.campanha.domain.Campanha;
import br.com.maxclubcard.campanhas.campanha.domain.repository.CampanhaRepository;
import br.com.maxclubcard.campanhas.shared.exceptions.NotFoundException;
import br.com.maxclubcard.campanhas.shared.exceptions.ValidationMessage;
import br.com.maxclubcard.campanhas.shared.exceptions.Validations;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CampanhaFinderImpl implements CampanhaFinder {

  private final CampanhaRepository repository;

  public CampanhaFinderImpl(CampanhaRepository repository) {
    this.repository = repository;
  }

  @Override
  public Campanha buscarPorId(Long idCampanha) {
    Validations.isNotNull(idCampanha, ValidationMessage.ID_CAMPANHA_OBRIGATORIO);

    return repository.findById(idCampanha)
        .orElseThrow(() -> {
          throw new NotFoundException(ValidationMessage.CAMPANHA_NAO_ENCONTRADA, idCampanha);
        });
  }

  @Override
  public List<Campanha> buscarPorClienteEValorCampanha(BigDecimal valor, String cpf) {
    Validations.isNotNull(cpf, ValidationMessage.CPF_OBRIGATORIO);
    Validations.isNotNull(valor, ValidationMessage.VALOR_MINIMO_OBRIGATORIO);

    return repository.findByValorMinimoLessThanEqualAndClientes_Cpf(valor, cpf);
  }
}
