package br.com.maxclubcard.campanhas.campanha.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import br.com.maxclubcard.campanhas.shared.exceptions.InvalidArgumentException;
import br.com.maxclubcard.campanhas.shared.exceptions.RequiredArgumentException;
import br.com.maxclubcard.campanhas.shared.exceptions.ValidationMessage;
import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CampanhaTest {

    private final String nome = "MaxClubCard nas Olimpiadas";
    private final BigDecimal valorMinimo = BigDecimal.valueOf(150);

    @DisplayName("Deve criar uma campanha com todas as informações fornecidas")
    @Test
    void testCriar(){
        //given
        Campanha campanha = new Campanha(nome, valorMinimo);

        //then
        assertEquals(nome, campanha.getNome());
        assertEquals(valorMinimo, campanha.getValorMinimo());
    }

    @DisplayName("Deve lançar exceção ao tentar criar campanha com dados inválidos")
    @Test
    void testCriarCampanha_DadosInvalidos(){
        //when
        Throwable exceptionNomeNulo = assertThrows(RequiredArgumentException.class, () ->
            new Campanha(null, valorMinimo));
        //then
        assertEquals(ValidationMessage.NOME_OBRIGATORIO.getMessage(), exceptionNomeNulo.getMessage());

        //when
        Throwable exceptionNomeVazio = assertThrows(InvalidArgumentException.class, () ->
            new Campanha("", valorMinimo));
        //then
        assertEquals(ValidationMessage.NOME_OBRIGATORIO.getMessage(), exceptionNomeVazio.getMessage());

        //when
        Throwable exceptionValorNulo = assertThrows(RequiredArgumentException.class, () ->
            new Campanha(nome, null));
        //then
        assertEquals(ValidationMessage.VALOR_MINIMO_OBRIGATORIO.getMessage(), exceptionValorNulo.getMessage());
    }
}
