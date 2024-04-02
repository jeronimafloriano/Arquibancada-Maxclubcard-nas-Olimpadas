package br.com.maxclubcard.campanhas.cliente.domain;

import static br.com.maxclubcard.campanhas.ApplicationFactoryTests.umCliente;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import br.com.maxclubcard.campanhas.shared.exceptions.InvalidArgumentException;
import br.com.maxclubcard.campanhas.shared.exceptions.RequiredArgumentException;
import br.com.maxclubcard.campanhas.shared.exceptions.ValidationMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ClienteTest {

  private final Cliente cliente = umCliente();

  @DisplayName("Deve criar um cliente com todas as informações fornecidas")
  @Test
  void testCriar() {
    //given
    Cliente novoCliente = Cliente.builder().nome(cliente.getNome())
        .cpf(cliente.getCpf()).dataNascimento(cliente.getDataNascimento())
        .sexo(cliente.getSexo()).email(cliente.getEmail()).celular(cliente.getCelular()).build();

    //then
    assertEquals(cliente.getNome(), novoCliente.getNome());
    assertEquals(cliente.getCpf(), novoCliente.getCpf());
    assertEquals(cliente.getDataNascimento(), novoCliente.getDataNascimento());
    assertEquals(cliente.getSexo(), novoCliente.getSexo());
    assertEquals(cliente.getEmail(), novoCliente.getEmail());
    assertEquals(cliente.getCelular(), novoCliente.getCelular());
  }

  @DisplayName("Deve lançar exceção ao tentar criar um cliente com dados inválidos")
  @Test
  void testCriar_DadosInvalidos() {
    //when
    Throwable exceptionNomeNulo = assertThrows(RequiredArgumentException.class,
        () ->  Cliente.builder().nome(null)
            .cpf(cliente.getCpf()).dataNascimento(cliente.getDataNascimento())
            .sexo(cliente.getSexo()).email(cliente.getEmail()).celular(cliente.getCelular()).build());
    //then
    assertEquals(ValidationMessage.NOME_OBRIGATORIO.getMessage(),
        exceptionNomeNulo.getMessage());

    //when
    Throwable exceptionNomeVazio = assertThrows(InvalidArgumentException.class,
        () ->  Cliente.builder().nome("")
            .cpf(cliente.getCpf()).dataNascimento(cliente.getDataNascimento())
            .sexo(cliente.getSexo()).email(cliente.getEmail()).celular(cliente.getCelular()).build());
    //then
    assertEquals(ValidationMessage.NOME_OBRIGATORIO.getMessage(),
        exceptionNomeVazio.getMessage());

    //when
    Throwable exceptionCpfNulo = assertThrows(RequiredArgumentException.class,
        () ->  Cliente.builder().nome(cliente.getNome())
            .cpf(null).dataNascimento(cliente.getDataNascimento())
            .sexo(cliente.getSexo()).email(cliente.getEmail()).celular(cliente.getCelular()).build());
    //then
    assertEquals(ValidationMessage.CPF_OBRIGATORIO.getMessage(),
        exceptionCpfNulo.getMessage());

    //when
    Throwable exceptionCpfVazio = assertThrows(InvalidArgumentException.class,
        () ->  Cliente.builder().nome(cliente.getNome())
            .cpf("").dataNascimento(cliente.getDataNascimento())
            .sexo(cliente.getSexo()).email(cliente.getEmail()).celular(cliente.getCelular()).build());
    //then
    assertEquals(ValidationMessage.CPF_OBRIGATORIO.getMessage(),
        exceptionCpfVazio.getMessage());

    //when
    Throwable exceptionDataNascimentoNula = assertThrows(RequiredArgumentException.class,
        () ->  Cliente.builder().nome(cliente.getNome())
            .cpf(cliente.getCpf()).dataNascimento(null)
            .sexo(cliente.getSexo()).email(cliente.getEmail()).celular(cliente.getCelular()).build());
    //then
    assertEquals(ValidationMessage.DATA_NASCIMENTO_OBRIGATORIA.getMessage(),
        exceptionDataNascimentoNula.getMessage());

    //when
    Throwable exceptionSexoNulo = assertThrows(RequiredArgumentException.class,
        () ->  Cliente.builder().nome(cliente.getNome())
            .cpf(cliente.getCpf()).dataNascimento(cliente.getDataNascimento())
            .sexo(null).email(cliente.getEmail()).celular(cliente.getCelular()).build());
    //then
    assertEquals(ValidationMessage.SEXO_OBRIGATORIO.getMessage(),
        exceptionSexoNulo.getMessage());

    //when
    Throwable exceptionEmailNulo = assertThrows(RequiredArgumentException.class,
        () ->  Cliente.builder().nome(cliente.getNome())
            .cpf(cliente.getCpf()).dataNascimento(cliente.getDataNascimento())
            .sexo(cliente.getSexo()).email(null).celular(cliente.getCelular()).build());
    //then
    assertEquals(ValidationMessage.EMAIL_OBRIGATORIO.getMessage(),
        exceptionEmailNulo.getMessage());

    //when
    Throwable exceptionCelularNulo = assertThrows(RequiredArgumentException.class,
        () ->  Cliente.builder().nome(cliente.getNome())
            .cpf(cliente.getCpf()).dataNascimento(cliente.getDataNascimento())
            .sexo(cliente.getSexo()).email(cliente.getEmail()).celular(null).build());
    //then
    assertEquals(ValidationMessage.CELULAR_OBRIGATORIO.getMessage(),
        exceptionCelularNulo.getMessage());
  }
}
