package br.com.maxclubcard.cliente.application.service;

import br.com.maxclubcard.cliente.domain.Cliente;
import br.com.maxclubcard.cliente.domain.Email;
import br.com.maxclubcard.cliente.domain.Sexo;
import br.com.maxclubcard.cliente.domain.repository.ClienteRepository;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ClienteServiceImpl implements ClienteService {

  private final ClienteRepository clienteRepository;

  public ClienteServiceImpl(
      ClienteRepository clienteRepository) {
    this.clienteRepository = clienteRepository;
  }

  @Override
  public Cliente converterCliente(List<String> dado) {
    String nome = dado.get(0);
    String cpf = dado.get(1);
    LocalDate nascimento = LocalDate.parse(dado.get(2), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    Sexo sexo = Sexo.convert(dado.get(3));
    Email email = Email.of(dado.get(4));
    String celular = dado.get(5);

    return Cliente.builder().nome(nome).cpf(cpf)
        .dataNascimento(nascimento).sexo(sexo).email(email)
        .celular(celular).build();
  }

  @Override
  public void salvar(List<Cliente> clientes) {
    clienteRepository.saveAll(clientes);
  }
}
