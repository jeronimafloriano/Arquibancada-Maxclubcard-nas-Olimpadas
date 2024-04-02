package br.com.maxclubcard.campanhas.sorteio.domain;

import static br.com.maxclubcard.campanhas.ApplicationFactoryTests.umCliente;
import static br.com.maxclubcard.campanhas.ApplicationFactoryTests.umaCampanha;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import br.com.maxclubcard.campanhas.campanha.domain.Campanha;
import br.com.maxclubcard.campanhas.cliente.domain.Cliente;
import br.com.maxclubcard.campanhas.shared.exceptions.RequiredArgumentException;
import br.com.maxclubcard.campanhas.shared.exceptions.ValidationMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SorteioParticipanteTest {

  private final Cliente cliente = umCliente();
  private final Campanha campanha = umaCampanha();

  @DisplayName("Deve criar um registro de participante de sorteio com todas as informações fornecidas")
  @Test
  void testCriar() {
    //given
    SorteioParticipante participante = new SorteioParticipante(12345l, campanha, cliente);

    //then
    assertEquals(campanha, participante.getCampanha());
    assertEquals(cliente, participante.getCliente());
    assertEquals(12345l, participante.getNumeroDaSorte());
  }

  @DisplayName("Deve lançar exceção ao tentar criar um um registro de participante de sorteio com dados inválidos")
  @Test
  void testCriar_DadosInvalidos() {
    //when
    Throwable exceptionNumeroDaSorteNulo = assertThrows(RequiredArgumentException.class,
        () -> new SorteioParticipante(null, campanha, cliente));
    //then
    assertEquals(ValidationMessage.NUMERO_DA_SORTE_OBRIGATORIO.getMessage(),
        exceptionNumeroDaSorteNulo.getMessage());

    //when
    Throwable exceptionCampanhaNula = assertThrows(RequiredArgumentException.class,
        () -> new SorteioParticipante(4567l, null, cliente));
    //then
    assertEquals(ValidationMessage.CAMPANHA_OBRIGATORIA.getMessage(),
        exceptionCampanhaNula.getMessage());

    //when
    Throwable exceptionClienteNulo = assertThrows(RequiredArgumentException.class,
        () -> new SorteioParticipante(4567l, campanha, null));
    //then
    assertEquals(ValidationMessage.CLIENTE_OBRIGATORIO.getMessage(),
        exceptionClienteNulo.getMessage());
  }
}
