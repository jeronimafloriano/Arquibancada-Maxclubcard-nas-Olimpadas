package br.com.maxclubcard.cliente.application.service;

import br.com.maxclubcard.cliente.domain.Cliente;
import java.util.List;

public interface ClienteService {

  Cliente converterCliente(List<String> dado);

  void salvar(List<Cliente> clientes);

}
