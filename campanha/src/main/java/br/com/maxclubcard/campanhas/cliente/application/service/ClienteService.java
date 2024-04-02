package br.com.maxclubcard.campanhas.cliente.application.service;

import br.com.maxclubcard.campanhas.cliente.application.dto.ClienteDto;
import java.util.List;

public interface ClienteService {

  ClienteDto cadastrar(Long idCampanha, ClienteDto clienteDto);

  List<ClienteDto> adicionarViaArquivo(Long idCampanha);
}
