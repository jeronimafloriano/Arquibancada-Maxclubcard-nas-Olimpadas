package br.com.maxclubcard.campanhas.cartao.service;

import static br.com.maxclubcard.campanhas.ApplicationFactoryTests.umCliente;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.maxclubcard.campanhas.cartao.application.dto.CartaoDto;
import br.com.maxclubcard.campanhas.cartao.application.service.impl.CartaoServiceImpl;
import br.com.maxclubcard.campanhas.cartao.domain.Bandeira;
import br.com.maxclubcard.campanhas.cartao.domain.Cartao;
import br.com.maxclubcard.campanhas.cartao.domain.Tipo;
import br.com.maxclubcard.campanhas.cartao.domain.repository.CartaoRepository;
import br.com.maxclubcard.campanhas.cliente.application.service.finder.ClienteFinder;
import br.com.maxclubcard.campanhas.shared.exceptions.DuplicatedException;
import br.com.maxclubcard.campanhas.shared.exceptions.ValidationMessage;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CartaoServiceTest {

  @Mock
  CartaoRepository repository;

  @Mock
  ClienteFinder clienteFinder;

  @InjectMocks
  CartaoServiceImpl cartaoService;

  private Cartao cartao;

  @BeforeEach
  void setUp() {
    this.cartao = new Cartao(umCliente(), "6011524294577112", Tipo.CREDITO, Bandeira.MAXCLUBCARD,
        LocalDate.parse("01/01/" + (LocalDate.now().getYear() + 10),
            DateTimeFormatter.ofPattern("dd/MM/yyyy")));
  }

  @DisplayName("Deve cadastrar um cartão")
  @Test
  void testCadastar() {
    //given
    String dataString = "01/01/" + (LocalDate.now().getYear() + 10);

    CartaoDto cartaoDto = new CartaoDto(cartao.getCliente().getId(), cartao.getNumero(),
        cartao.getTipo(),
        cartao.getBandeira(), dataString);

    when(clienteFinder.buscarPorId(cartao.getCliente().getId())).thenReturn(cartao.getCliente());
    when(
        repository.findByNumeroAndBandeira(cartao.getNumero(), cartaoDto.getBandeira())).thenReturn(
        Optional.empty());

    //when
    ArgumentCaptor<Cartao> captor = ArgumentCaptor.forClass(Cartao.class);

    var resultado = cartaoService.cadastrar(cartaoDto);

    //then
    verify(repository, times(1)).save(captor.capture());

    Cartao cartaoCriado = captor.getValue();

    assertEquals(cartaoDto.getIdCliente(), resultado.getIdCliente());
    assertEquals(cartaoDto.getNumero(), resultado.getNumero());
    assertEquals(cartaoDto.getTipo(), resultado.getTipo());
    assertEquals(cartaoDto.getBandeira(), resultado.getBandeira());

    assertEquals(parseData(cartaoDto.getDataExpiracao()).toString(), resultado.getDataExpiracao());

    assertEquals(cartaoDto.getIdCliente(), cartaoCriado.getCliente().getId());
    assertEquals(cartaoDto.getNumero(), cartaoCriado.getNumero());
    assertEquals(cartaoDto.getTipo(), cartaoCriado.getTipo());
    assertEquals(cartaoDto.getBandeira(), cartaoCriado.getBandeira());
    assertEquals(parseData(cartaoDto.getDataExpiracao()).toString(),
        cartaoCriado.getDataExpiracao().toString());
  }

  @DisplayName("Deve lançar exceção ao tentar cadastrar um cartão com numero e bandeira já existente")
  @Test
  void testCadastrar_NumeroEBandeiraDuplicados() {
    //given
    CartaoDto cartaoDto = new CartaoDto(cartao.getCliente().getId(), cartao.getNumero(),
        cartao.getTipo(),
        cartao.getBandeira(), cartao.getDataExpiracao().toString());

    when(clienteFinder.buscarPorId(cartao.getCliente().getId())).thenReturn(cartao.getCliente());
    when(
        repository.findByNumeroAndBandeira(cartao.getNumero(), cartaoDto.getBandeira())).thenReturn(
        Optional.of(cartao));

    //when
    Throwable exceptionCartaoDuplicado = assertThrows(DuplicatedException.class, () -> {
      cartaoService.cadastrar(cartaoDto);
    });

    //then
    assertEquals(ValidationMessage.NUMERO_BANDEIRA_CARTAO_JA_CADASTRADO.getMessage(),
        exceptionCartaoDuplicado.getMessage());

    verify(repository, never()).save(any());
  }

  private LocalDate parseData(String data) {
    return LocalDate.parse(data,
        DateTimeFormatter.ofPattern("dd/MM/yyyy"));
  }
}
