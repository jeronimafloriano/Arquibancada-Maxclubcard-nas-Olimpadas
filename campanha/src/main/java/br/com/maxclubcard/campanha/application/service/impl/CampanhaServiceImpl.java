package br.com.maxclubcard.campanha.application.service.impl;


import br.com.maxclubcard.campanha.application.dto.CampanhaDto;
import br.com.maxclubcard.campanha.application.service.CampanhaService;
import br.com.maxclubcard.config.leitor.LeitorArquivoStrategy;
import br.com.maxclubcard.config.leitor.impl.LeitorExcel;
import br.com.maxclubcard.campanha.domain.Campanha;
import br.com.maxclubcard.campanha.domain.repository.CampanhaRepository;
import br.com.maxclubcard.cartao.domain.Cartao;
import br.com.maxclubcard.cartao.application.service.CartaoService;
import br.com.maxclubcard.cliente.application.service.ClienteService;
import br.com.maxclubcard.cliente.domain.Cliente;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CampanhaServiceImpl implements CampanhaService {

  @Value("${path.arquivo.campanha}")
  private String caminhoArquivo;

  private final CampanhaRepository campanhaRepository;
  private final ClienteService clienteService;
  private final CartaoService cartaoService;

  public CampanhaServiceImpl(CampanhaRepository campanhaRepository,
      ClienteService clienteServie, CartaoService cartaoService) {
    this.campanhaRepository = campanhaRepository;
    this.clienteService = clienteServie;
    this.cartaoService = cartaoService;
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

  public CampanhaDto adicionarViaArquivo(Long idCampanha) {
    Campanha campanha = campanhaRepository.findById(idCampanha)
        .orElseThrow(RuntimeException::new);

    LeitorExcel leitor = new LeitorExcel();
    LeitorArquivoStrategy strategy = new LeitorArquivoStrategy(leitor);

    Collection<List<String>> dados = strategy.lerArquivo(caminhoArquivo);
    List<Cartao> cartoes = new ArrayList<>();
    List<Cliente> clientes = new ArrayList<>();

    for (List<String> dado : dados) {
      Cliente cliente = clienteService.converterCliente(dado);
      Cartao cartao = cartaoService.converterCartao(dado, cliente);

      cliente.vincularCampanha(campanha);
      cliente.vincularCartao(cartao);

      cartoes.add(cartao);
      clientes.add(cliente);
    }

    cartaoService.salvar(cartoes);
    clienteService.salvar(clientes);

    campanha.vincularClientes(clientes);
    campanhaRepository.save(campanha);
    return CampanhaDto.map(campanha);
  }

}
