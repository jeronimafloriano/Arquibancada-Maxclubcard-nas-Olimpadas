package br.com.maxclubcard.campanhas.cliente.application.service.impl;

import br.com.maxclubcard.campanhas.campanha.application.service.finder.CampanhaFinder;
import br.com.maxclubcard.campanhas.campanha.domain.Campanha;
import br.com.maxclubcard.campanhas.cartao.domain.Cartao;
import br.com.maxclubcard.campanhas.cliente.application.dto.ClienteDto;
import br.com.maxclubcard.campanhas.cliente.application.service.ClienteService;
import br.com.maxclubcard.campanhas.cliente.domain.Cliente;
import br.com.maxclubcard.campanhas.cliente.domain.Email;
import br.com.maxclubcard.campanhas.cliente.domain.repository.ClienteRepository;
import br.com.maxclubcard.campanhas.config.leitor.LeitorArquivoStrategy;
import br.com.maxclubcard.campanhas.config.leitor.impl.LeitorExcel;
import br.com.maxclubcard.campanhas.shared.events.CampanhaAtualizadaEvent;
import br.com.maxclubcard.campanhas.shared.exceptions.ValidationMessage;
import br.com.maxclubcard.campanhas.shared.exceptions.Validations;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClienteServiceImpl implements ClienteService {

  @Value("${path.arquivo.campanha}")
  private String caminhoArquivo;

  private final ClienteRepository clienteRepository;
  private final CampanhaFinder campanhaFinder;
  private final ApplicationEventPublisher eventPublisher;

  public ClienteServiceImpl(ClienteRepository clienteRepository,
      CampanhaFinder campanhaFinder,
      ApplicationEventPublisher eventPublisher) {
    this.clienteRepository = clienteRepository;
    this.campanhaFinder = campanhaFinder;
    this.eventPublisher = eventPublisher;
  }

  @Transactional
  @Override
  public ClienteDto cadastrar(Long idCampanha, ClienteDto clienteDto) {
    Validations.isNotNull(idCampanha, ValidationMessage.ID_CAMPANHA_OBRIGATORIO);
    Validations.isNotNull(clienteDto, ValidationMessage.CLIENTE_OBRIGATORIO);

    Cliente cliente = Cliente.builder().nome(clienteDto.getNome()).cpf(clienteDto.getCpf())
        .dataNascimento(LocalDate.parse(clienteDto.getDataNascimento(),
            DateTimeFormatter.ofPattern("dd/MM/yyyy")))
        .sexo(clienteDto.getSexo()).email(Email.of(clienteDto.getEmail()))
        .celular(clienteDto.getCelular()).build();

    clienteRepository.save(cliente);
    return ClienteDto.map(cliente);
  }

  @Transactional
  @Override
  public List<ClienteDto> adicionarViaArquivo(Long idCampanha) {
    Validations.isNotNull(idCampanha, ValidationMessage.ID_CAMPANHA_OBRIGATORIO);

    Campanha campanha = campanhaFinder.buscarPorId(idCampanha);

    LeitorExcel leitor = new LeitorExcel();
    LeitorArquivoStrategy strategy = new LeitorArquivoStrategy(leitor);

    Collection<List<String>> dados = strategy.lerArquivo(caminhoArquivo);

    Set<Cliente> clientes = converterDados(campanha, dados);
    clienteRepository.saveAll(clientes);

    campanha.vincularClientes(clientes);
    eventPublisher.publishEvent(new CampanhaAtualizadaEvent(campanha));

    return clientes.stream().map(ClienteDto::map).toList();
  }

  private Set<Cliente> converterDados(Campanha campanha,
      Collection<List<String>> dados) {
    Set<Cliente> clientes = new HashSet<>();

    for (List<String> dado : dados) {
      String cpf = dado.get(1);
      Cliente cliente;

      Optional<Cliente> clienteExistente = clientes.stream()
          .filter(c -> c.getCpf().equals(cpf)).findFirst();

      if (clienteExistente.isPresent()) {
        cliente = clienteExistente.get();
      } else {
        cliente = Cliente.gerarCliente(dado, campanha);
        clientes.add(cliente);
      }

      Cartao cartao = Cartao.gerarCartao(dado, cliente);
      cliente.vincularCartao(cartao);
    }
    return clientes;
  }
}
