package br.com.maxclubcard.campanhas.cliente.application.service.finder.impl;

import br.com.maxclubcard.campanhas.cliente.domain.Cliente;
import br.com.maxclubcard.campanhas.cliente.domain.repository.ClienteRepository;
import br.com.maxclubcard.campanhas.shared.exceptions.NotFoundException;
import br.com.maxclubcard.campanhas.shared.exceptions.ValidationMessage;
import br.com.maxclubcard.campanhas.cliente.application.service.finder.ClienteFinder;

import org.springframework.stereotype.Service;

@Service
public class ClienteFinderImpl implements ClienteFinder {

  private final ClienteRepository repository;

  public ClienteFinderImpl(ClienteRepository repository) {
    this.repository = repository;
  }

  @Override
  public Cliente buscarPorId(Long idCliente) {
    return repository.findById(idCliente)
        .orElseThrow(() -> {
          throw new NotFoundException(ValidationMessage.CLIENTE_NAO_ENCONTRADO, idCliente);
        });
  }

  @Override
  public Cliente buscarPorCpf(String cpf) {
    return repository.findByCpf(cpf)
        .orElseThrow(() -> {
          throw new NotFoundException(ValidationMessage.CLIENTE_NAO_ENCONTRADO, cpf);
        });
  }
}
