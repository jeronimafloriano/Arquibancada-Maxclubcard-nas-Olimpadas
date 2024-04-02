package br.com.maxclubcard.campanhas.campanha.application.service.impl;

import br.com.maxclubcard.campanhas.campanha.application.dto.CampanhaDto;
import br.com.maxclubcard.campanhas.campanha.application.service.CampanhaService;
import br.com.maxclubcard.campanhas.campanha.domain.Campanha;
import br.com.maxclubcard.campanhas.campanha.domain.repository.CampanhaRepository;
import br.com.maxclubcard.campanhas.shared.events.CampanhaAtualizadaEvent;
import br.com.maxclubcard.campanhas.shared.exceptions.DuplicatedException;
import br.com.maxclubcard.campanhas.shared.exceptions.ValidationMessage;
import br.com.maxclubcard.campanhas.shared.exceptions.Validations;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CampanhaServiceImpl implements CampanhaService {

  @Value("${path.arquivo.campanha}")
  private String caminhoArquivo;

  private final CampanhaRepository campanhaRepository;

  public CampanhaServiceImpl(CampanhaRepository campanhaRepository) {
    this.campanhaRepository = campanhaRepository;
  }

  @Transactional(readOnly = true)
  @Override
  public List<CampanhaDto> buscar() {
    List<Campanha> campanhas = campanhaRepository.findAll();
    return campanhas.stream()
        .map(CampanhaDto::map)
        .toList();
  }

  @Transactional
  @Override
  public CampanhaDto cadastrar(CampanhaDto campanhaDto) {
    Validations.isNotNull(campanhaDto, ValidationMessage.CAMPANHA_OBRIGATORIA);

    validarDuplicidadeNome(campanhaDto.getNome());

    Campanha campanha = new Campanha(campanhaDto.getNome(), campanhaDto.getValorMinimo());
    campanhaRepository.save(campanha);
    return CampanhaDto.map(campanha);
  }

  @EventListener(CampanhaAtualizadaEvent.class)
  @Transactional
  public void onApplicationEvent(CampanhaAtualizadaEvent event) {
    campanhaRepository.save(event.getCampanha());
  }

  private void validarDuplicidadeNome(String nome) {
    campanhaRepository.findByNome(nome)
        .ifPresent(campanha -> {
          throw new DuplicatedException(ValidationMessage.NOME_CAMPANHA_JA_EXISTE);
        });
  }
}
