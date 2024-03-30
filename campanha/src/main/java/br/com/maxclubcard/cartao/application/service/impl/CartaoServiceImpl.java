package br.com.maxclubcard.cartao.application.service.impl;

import br.com.maxclubcard.cartao.domain.Bandeira;
import br.com.maxclubcard.cartao.domain.Cartao;
import br.com.maxclubcard.cartao.domain.Tipo;
import br.com.maxclubcard.cartao.application.service.CartaoService;
import br.com.maxclubcard.cartao.domain.repository.CartaoRepository;
import br.com.maxclubcard.cliente.domain.Cliente;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CartaoServiceImpl implements CartaoService {

  private final CartaoRepository cartaoRepository;

  public CartaoServiceImpl(
      CartaoRepository cartaoRepository) {
    this.cartaoRepository = cartaoRepository;
  }

  @Override
  public Cartao converterCartao(List<String> dado, Cliente cliente) {
    String numeroCartao = dado.get(6);
    LocalDate dataExp = LocalDate.parse(dado.get(7), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    Tipo tipo = Tipo.convert(dado.get(8));
    Bandeira bandeira = Bandeira.convert(dado.get(9));
    return Cartao.builder().numero(numeroCartao).dataExpiracao(dataExp).tipo(tipo)
        .bandeira(bandeira).cliente(cliente).build();
  }

  @Override
  public void salvar(List<Cartao> cartoes) {
    cartaoRepository.saveAll(cartoes);
  }
}
