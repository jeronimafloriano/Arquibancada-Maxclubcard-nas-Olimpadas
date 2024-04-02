package br.com.maxclubcard.campanhas.cartao.domain;

import static br.com.maxclubcard.campanhas.ApplicationFactoryTests.umCliente;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import br.com.maxclubcard.campanhas.cliente.domain.Cliente;
import br.com.maxclubcard.campanhas.shared.exceptions.InvalidArgumentException;
import br.com.maxclubcard.campanhas.shared.exceptions.RequiredArgumentException;
import br.com.maxclubcard.campanhas.shared.exceptions.ValidationMessage;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CartaoTest {

  private final Cliente cliente = umCliente();
  private final String numeroCartao = "6011 5242 9457 7112";
  private final LocalDate dataExpiracao = LocalDate.of(LocalDate.now().getYear() + 10, 12, 01);
  ;

  @DisplayName("Deve criar um cartão com todas as informações fornecidas")
  @Test
  void testCriar() {
    //given
    Cartao cartao = new Cartao(cliente, numeroCartao, Tipo.CREDITO, Bandeira.MAXCLUBCARD,
        dataExpiracao);

    //then
    assertEquals(cliente, cartao.getCliente());
    assertEquals(numeroCartao, cartao.getNumero());
    assertEquals(Tipo.CREDITO, cartao.getTipo());
    assertEquals(Bandeira.MAXCLUBCARD, cartao.getBandeira());
    assertEquals(dataExpiracao, cartao.getDataExpiracao());
  }

  @DisplayName("Deve lançar exceção ao tentar criar um cartão com dados inválidos")
  @Test
  void testCriar_DadosInvalidos() {
    //when
    Throwable exceptionClienteNulo = assertThrows(RequiredArgumentException.class,
        () -> new Cartao(null, numeroCartao, Tipo.CREDITO, Bandeira.MAXCLUBCARD, dataExpiracao));
    //then
    assertEquals(ValidationMessage.CLIENTE_OBRIGATORIO.getMessage(),
        exceptionClienteNulo.getMessage());

    //when
    Throwable exceptionNumeroNulo = assertThrows(RequiredArgumentException.class,
        () -> new Cartao(cliente, null, Tipo.CREDITO, Bandeira.MAXCLUBCARD, dataExpiracao));
    //then
    assertEquals(ValidationMessage.NUMERO_CARTAO_OBRIGATORIO.getMessage(),
        exceptionNumeroNulo.getMessage());

    //when
    Throwable exceptionNumeroVazio = assertThrows(InvalidArgumentException.class,
        () -> new Cartao(cliente, "", Tipo.CREDITO, Bandeira.MAXCLUBCARD, dataExpiracao));
    //then
    assertEquals(ValidationMessage.NUMERO_CARTAO_OBRIGATORIO.getMessage(),
        exceptionNumeroVazio.getMessage());

    //when
    Throwable exceptionTipoNulo = assertThrows(RequiredArgumentException.class,
        () -> new Cartao(cliente, numeroCartao, null, Bandeira.MAXCLUBCARD, dataExpiracao));
    //then
    assertEquals(ValidationMessage.TIPO_OBRIGATORIO.getMessage(), exceptionTipoNulo.getMessage());

    //when
    Throwable exceptionBandeiraNula = assertThrows(RequiredArgumentException.class,
        () -> new Cartao(cliente, numeroCartao, Tipo.CREDITO, null, dataExpiracao));
    //then
    assertEquals(ValidationMessage.BANDEIRA_OBRIGATORIA.getMessage(),
        exceptionBandeiraNula.getMessage());

    //when
    Throwable exceptionDataExpiracaoNula = assertThrows(RequiredArgumentException.class,
        () -> new Cartao(cliente, numeroCartao, Tipo.DEBITO, Bandeira.MAXCLUBCARD, null));
    //then
    assertEquals(ValidationMessage.DATA_EXPIRACAO_OBRIGATORIA.getMessage(),
        exceptionDataExpiracaoNula.getMessage());
  }
}
