package br.com.maxclubcard.campanhas;

import br.com.maxclubcard.campanhas.campanha.domain.Campanha;
import br.com.maxclubcard.campanhas.cartao.domain.Cartao;
import br.com.maxclubcard.campanhas.cliente.domain.Cliente;
import br.com.maxclubcard.campanhas.cliente.domain.Email;
import br.com.maxclubcard.campanhas.cliente.domain.Sexo;
import java.math.BigDecimal;
import java.time.LocalDate;

public class ApplicationFactoryTests {

  private static final String nome = "Jeronima";
  private static final String cpf = "514.404.010-12";
  private static final LocalDate dataNascimento = LocalDate.of(1996,12,27);
  private static final Email email = Email.of("teste@email.com");

  public static Cliente umCliente() {
    return new Cliente(nome, cpf, dataNascimento, Sexo.FEMININO, email, "(62) 91234-5098");
  }

  public static Campanha umaCampanha() {
    return new Campanha("MaxClubCard", BigDecimal.valueOf(150));
  }

}
