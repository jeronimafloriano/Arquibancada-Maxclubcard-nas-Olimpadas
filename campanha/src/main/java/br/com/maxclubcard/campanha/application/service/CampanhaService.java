package br.com.maxclubcard.campanha.application.service;

import br.com.maxclubcard.campanha.application.dto.CampanhaDto;
import java.util.List;

public interface CampanhaService {

  List<CampanhaDto> buscar();

  CampanhaDto cadastrar(CampanhaDto campanhaDto);

  CampanhaDto adicionarViaArquivo(Long idCampanha);
}
