package br.com.maxclubcard.campanha.application.service.impl;


import br.com.maxclubcard.campanha.application.dto.CampanhaDto;
import br.com.maxclubcard.campanha.application.service.CampanhaService;
import br.com.maxclubcard.campanha.domain.Campanha;
import br.com.maxclubcard.campanha.domain.repository.CampanhaRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CampanhaServiceImpl implements CampanhaService {

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
    Campanha campanha = new Campanha(campanhaDto.getNome());
    campanhaRepository.save(campanha);
    return CampanhaDto.map(campanha);
  }
}