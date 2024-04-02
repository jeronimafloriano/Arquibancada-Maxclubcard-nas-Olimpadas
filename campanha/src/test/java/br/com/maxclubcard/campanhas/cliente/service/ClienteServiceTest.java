package br.com.maxclubcard.campanhas.cliente.service;

import static br.com.maxclubcard.campanhas.ApplicationFactoryTests.umCliente;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import br.com.maxclubcard.campanhas.campanha.application.service.finder.CampanhaFinder;
import br.com.maxclubcard.campanhas.cliente.application.dto.ClienteDto;
import br.com.maxclubcard.campanhas.cliente.application.service.impl.ClienteServiceImpl;
import br.com.maxclubcard.campanhas.cliente.domain.Cliente;
import br.com.maxclubcard.campanhas.cliente.domain.repository.ClienteRepository;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceTest {

  @Mock
  ClienteRepository repository;

  @Mock
  CampanhaFinder campanhaFinder;

  @InjectMocks
  ClienteServiceImpl clienteService;

  private Cliente cliente;

  @BeforeEach
  void setUp() {
    this.cliente = umCliente();
  }

  @DisplayName("Deve cadastrar um cliente")
  @Test
  void testCadastar() {
    //given
    String dataString = "01/01/" + (LocalDate.now().getYear() - 20);

    ClienteDto clienteDto = new ClienteDto(cliente.getNome(), cliente.getCpf(),
        dataString,
        cliente.getSexo(), cliente.getEmail().getAddress(), cliente.getCelular());

    //when
    ArgumentCaptor<Cliente> captor = ArgumentCaptor.forClass(Cliente.class);

    var resultado = clienteService.cadastrar(1l, clienteDto);

    //then
    verify(repository, times(1)).save(captor.capture());

    Cliente clienteCriado = captor.getValue();

    assertEquals(clienteDto.getNome(), resultado.getNome());
    assertEquals(clienteDto.getCpf(), resultado.getCpf());
    assertEquals(parseData(clienteDto.getDataNascimento()).toString(), resultado.getDataNascimento());
    assertEquals(clienteDto.getSexo(), resultado.getSexo());
    assertEquals(clienteDto.getEmail(), resultado.getEmail());
    assertEquals(clienteDto.getCelular(), resultado.getCelular());

    assertEquals(clienteDto.getNome(), clienteCriado.getNome());
    assertEquals(clienteDto.getCpf(), clienteCriado.getCpf());
    assertEquals(parseData(clienteDto.getDataNascimento()).toString(), clienteCriado.getDataNascimento().toString());
    assertEquals(clienteDto.getSexo(), clienteCriado.getSexo());
    assertEquals(clienteDto.getEmail(), clienteCriado.getEmail().getAddress());
    assertEquals(clienteDto.getCelular(), clienteCriado.getCelular());
  }

  private LocalDate parseData(String data) {
    return LocalDate.parse(data,
        DateTimeFormatter.ofPattern("dd/MM/yyyy"));
  }
}
