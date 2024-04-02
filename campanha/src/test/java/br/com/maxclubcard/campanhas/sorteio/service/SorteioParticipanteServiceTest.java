package br.com.maxclubcard.campanhas.sorteio.service;

import static br.com.maxclubcard.campanhas.ApplicationFactoryTests.umCliente;
import static br.com.maxclubcard.campanhas.ApplicationFactoryTests.umaCampanha;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.maxclubcard.campanhas.campanha.application.service.finder.impl.CampanhaFinderImpl;
import br.com.maxclubcard.campanhas.cliente.application.service.finder.ClienteFinder;
import br.com.maxclubcard.campanhas.cliente.domain.Cliente;
import br.com.maxclubcard.campanhas.cliente.domain.Email;
import br.com.maxclubcard.campanhas.cliente.domain.Sexo;
import br.com.maxclubcard.campanhas.shared.events.TransacaoRecebidaEvent;
import br.com.maxclubcard.campanhas.sorteio.application.dto.SorteioParticipanteDto;
import br.com.maxclubcard.campanhas.sorteio.application.service.impl.SorteioParticipanteServiceImpl;
import br.com.maxclubcard.campanhas.sorteio.domain.SorteioParticipante;
import br.com.maxclubcard.campanhas.sorteio.domain.repository.SorteioParticipanteRepository;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class SorteioParticipanteServiceTest {

  @Mock
  SorteioParticipanteRepository repository;

  @Mock
  CampanhaFinderImpl campanhaFinder;

  @Mock
  ClienteFinder clienteFinder;

  @InjectMocks
  SorteioParticipanteServiceImpl sorteioService;

  private SorteioParticipante sorteioParticipante;

  @BeforeEach
  void setUp() {
    this.sorteioParticipante = new SorteioParticipante(12345l, umaCampanha(), umCliente());
  }

  @Test
  @DisplayName("Deve excluir os trechos de encomenda quando uma localidade associada for excluída")
  void testOnApplicationEvent_TransacaoRecebidaEvent() {
    String transacao = "id=1,cpf=65488900987,valor=150,numero do cartao=1234567898765432,tipo=CREDITO";

    sorteioService.onApplicationEvent(
        new TransacaoRecebidaEvent(transacao));

    verify(repository, times(1)).saveAll(anyList());
  }

  @DisplayName("Deve cadastrar um cliente")
  @Test
  void testRealizarSorteio() {
    //given
    Cliente cliente = new Cliente("Joao", "345.777.888-99",
        LocalDate.of(1996, 12, 27), Sexo.MASCULINO, Email.of("teste@email.com"), "(62) 91234-5098");

    SorteioParticipante sorteio = new SorteioParticipante(12345l, umaCampanha(), cliente);

    List<SorteioParticipante> participantes = Arrays.asList(sorteioParticipante, sorteio);

    //
    when(repository.findRandomByCampanhaId(any())).thenReturn(participantes);
    SorteioParticipanteDto sorteado = sorteioService.realizarSorteio(1l);

    //then
    assertNotNull(sorteado);
  }
}
