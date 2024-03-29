package br.com.maxclubcard.campanha.application.service;

import br.com.maxclubcard.campanha.application.dto.CampanhaDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface CampanhaService {

  List<CampanhaDto> buscar();

  CampanhaDto cadastrar(CampanhaDto campanhaDto);
}
