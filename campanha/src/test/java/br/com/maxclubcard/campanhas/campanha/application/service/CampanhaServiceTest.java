package br.com.maxclubcard.campanhas.campanha.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.maxclubcard.campanhas.campanha.application.dto.CampanhaDto;
import br.com.maxclubcard.campanhas.campanha.application.service.impl.CampanhaServiceImpl;
import br.com.maxclubcard.campanhas.campanha.domain.Campanha;
import br.com.maxclubcard.campanhas.campanha.domain.repository.CampanhaRepository;
import br.com.maxclubcard.campanhas.shared.events.CampanhaAtualizadaEvent;
import br.com.maxclubcard.campanhas.shared.exceptions.DuplicatedException;
import br.com.maxclubcard.campanhas.shared.exceptions.ValidationMessage;
import java.math.BigDecimal;
import java.util.Arrays;
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
public class CampanhaServiceTest {

    @Mock
    CampanhaRepository repository;

    @InjectMocks
    CampanhaServiceImpl campanhaService;

    private Campanha campanha;

    @BeforeEach
    void setUp() {
        this.campanha = new Campanha("MaxClubCard", BigDecimal.valueOf(150));

    }

    @DisplayName("Deve buscar todas as campanhas cadastradas")
    @Test
    void testBuscar() {
        //given
        Campanha campanha = new Campanha("Nova campanha", BigDecimal.valueOf(500));

        when(repository.findAll()).thenReturn(Arrays.asList(campanha, this.campanha));

        //when
        var resultado = campanhaService.buscar();

        //then
        CampanhaDto resultadoUm = resultado.get(0);
        CampanhaDto resultadoDois = resultado.get(1);

        assertEquals(2, resultado.size());
        assertEquals(campanha.getNome(), resultadoUm.getNome());
        assertEquals(campanha.getValorMinimo(), resultadoUm.getValorMinimo());

        assertEquals(this.campanha.getNome(), resultadoDois.getNome());
        assertEquals(this.campanha.getValorMinimo(), resultadoDois.getValorMinimo());
    }

    @DisplayName("Deve cadastrar uma campanha")
    @Test
    void testCadastar() {
        //given
        CampanhaDto campanhaDto = new CampanhaDto(campanha.getNome(), campanha.getValorMinimo());

        when(repository.findByNome(campanhaDto.getNome())).thenReturn(Optional.empty());

        //when
        ArgumentCaptor<Campanha> captor = ArgumentCaptor.forClass(Campanha.class);

        var result = campanhaService.cadastrar(campanhaDto);

        //then
        verify(repository, times(1)).save(captor.capture());

        Campanha campanhaCriada = captor.getValue();

        assertEquals(campanhaDto.getNome(), result.getNome());
        assertEquals(campanhaDto.getValorMinimo(), result.getValorMinimo());

        assertEquals(campanhaDto.getNome(), campanhaCriada.getNome());
        assertEquals(campanhaDto.getValorMinimo(), result.getValorMinimo());
    }

    @DisplayName("Deve lançar exceção ao tentar cadastrar uma campanha com nome já existente")
    @Test
    void testCadastrar_NomeCampanhaDuplicado() {
        //given
        CampanhaDto campanhaDto = new CampanhaDto(campanha.getNome(), campanha.getValorMinimo());

        when(repository.findByNome(campanhaDto.getNome())).thenReturn(Optional.of(campanha));

        //when
        Throwable exceptionNomeDuplicado = assertThrows(DuplicatedException.class, () -> {
            campanhaService.cadastrar(campanhaDto);
        });

        //then
        assertEquals(ValidationMessage.NOME_CAMPANHA_JA_EXISTE.getMessage(),
            exceptionNomeDuplicado.getMessage());

        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Deve salvar os dados de campanha quando receber um evento de campanha atualizada")
    void testOnApplicationEvent_EventoLocalidadeEncomendaExcluida() {
        //when
        campanhaService.onApplicationEvent(
            new CampanhaAtualizadaEvent(campanha));

        //then
        verify(repository, times(1)).save(campanha);
    }
}
