package br.com.maxclubcard.campanhas.sorteio.application.service.impl;

import br.com.maxclubcard.campanhas.campanha.application.service.finder.CampanhaFinder;
import br.com.maxclubcard.campanhas.campanha.domain.Campanha;
import br.com.maxclubcard.campanhas.cliente.application.service.finder.ClienteFinder;
import br.com.maxclubcard.campanhas.cliente.domain.Cliente;
import br.com.maxclubcard.campanhas.events.TransacaoRecebidaEvent;
import br.com.maxclubcard.campanhas.shared.exceptions.ValidationMessage;
import br.com.maxclubcard.campanhas.shared.exceptions.Validations;
import br.com.maxclubcard.campanhas.sorteio.application.dto.SorteioParticipanteDto;
import br.com.maxclubcard.campanhas.sorteio.application.service.SorteioParticipanteService;
import br.com.maxclubcard.campanhas.sorteio.domain.SorteioParticipante;
import br.com.maxclubcard.campanhas.sorteio.domain.repository.SorteioParticipanteRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SorteioParticpanteServiceImpl implements SorteioParticipanteService {

  private final SorteioParticipanteRepository sorteioParticipanteRepository;
  private final CampanhaFinder campanhaFinder;
  private final ClienteFinder clienteFinder;
  private Random random;

  public SorteioParticpanteServiceImpl(
      SorteioParticipanteRepository sorteioParticipanteRepository,
      CampanhaFinder campanhaFinder,
      ClienteFinder clienteFinder) {
    this.sorteioParticipanteRepository = sorteioParticipanteRepository;
    this.campanhaFinder = campanhaFinder;
    this.clienteFinder = clienteFinder;
    random = new Random();
  }

  @Override
  public SorteioParticipanteDto realizarSorteio(Long campanhaId) {
    Validations.isNotNull(campanhaId, ValidationMessage.ID_CAMPANHA_OBRIGATORIO);

    List<SorteioParticipante> sorteioParticipante = sorteioParticipanteRepository.findRandomByCampanhaId(
        campanhaId);

    int indiceAleatorio = random.nextInt(sorteioParticipante.size());

    return SorteioParticipanteDto.map(sorteioParticipante.get(indiceAleatorio));
  }

  @EventListener(TransacaoRecebidaEvent.class)
  @Transactional
  public void onApplicationEvent(TransacaoRecebidaEvent event) {
    String[] partes = event.getMessage().split(",");
    String cpf = null;
    BigDecimal valor = null;

    for (String parte : partes) {
      if (parte.startsWith("cpf=")) {
        cpf = parte.substring(4);
      } else if (parte.startsWith("valor=")) {
        valor = BigDecimal.valueOf(Double.parseDouble(parte.substring(6)));
      }
    }

    gerarNumerosDaSorte(cpf, valor);
  }

  private void gerarNumerosDaSorte(String cpf, BigDecimal valor) {
    Long numeroDaSorte = 1000000 + random.nextLong((9999999 - 1000000 + 1));

    List<Campanha> campanhasElegiveisPorCpf = campanhaFinder.buscarPorClienteEValorCampanha(valor,
        cpf);
    Cliente cliente = clienteFinder.buscarPorCpf(cpf);

    List<SorteioParticipante> sorteioParticipante = campanhasElegiveisPorCpf.stream()
        .map(campanha ->
            new SorteioParticipante(numeroDaSorte, campanha, cliente)).toList();

    sorteioParticipanteRepository.saveAll(sorteioParticipante);
  }
}
