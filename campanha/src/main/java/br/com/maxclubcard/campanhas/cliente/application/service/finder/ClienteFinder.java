package br.com.maxclubcard.campanhas.cliente.application.service.finder;


import br.com.maxclubcard.campanhas.cliente.domain.Cliente;

public interface ClienteFinder {

  Cliente buscarPorId(Long idCliente);

  Cliente buscarPorCpf(String cpf);
}
