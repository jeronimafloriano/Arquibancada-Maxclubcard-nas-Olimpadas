package br.com.maxclubcard.campanhas.campanha.application.service;

import br.com.maxclubcard.campanhas.campanha.application.dto.CampanhaDto;
import java.util.List;

public interface CampanhaService {

  List<CampanhaDto> buscar();

  CampanhaDto cadastrar(CampanhaDto campanhaDto);
}
