package br.com.maxclubcard.campanhas.cartao.application.service.impl;

import br.com.maxclubcard.campanhas.cartao.application.dto.CartaoDto;
import br.com.maxclubcard.campanhas.cartao.domain.Bandeira;
import br.com.maxclubcard.campanhas.cartao.domain.Cartao;
import br.com.maxclubcard.campanhas.cartao.application.service.CartaoService;
import br.com.maxclubcard.campanhas.cartao.domain.repository.CartaoRepository;
import br.com.maxclubcard.campanhas.cliente.application.service.finder.ClienteFinder;
import br.com.maxclubcard.campanhas.cliente.domain.Cliente;
import br.com.maxclubcard.campanhas.shared.exceptions.DuplicatedException;
import br.com.maxclubcard.campanhas.shared.exceptions.ValidationMessage;
import br.com.maxclubcard.campanhas.shared.exceptions.Validations;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.springframework.stereotype.Service;

@Service
public class CartaoServiceImpl implements CartaoService {

  private final CartaoRepository cartaoRepository;
  private final ClienteFinder clienteFinder;

  public CartaoServiceImpl(
      CartaoRepository cartaoRepository, ClienteFinder clienteFinder) {
    this.cartaoRepository = cartaoRepository;
    this.clienteFinder = clienteFinder;
  }

  @Override
  public CartaoDto cadastrar(CartaoDto cartaoDto) {
    Validations.isNotNull(cartaoDto, ValidationMessage.CARTAO_OBRIGATORIO);

    Cliente cliente = clienteFinder.buscarPorId(cartaoDto.getIdCliente());

    validarDuplicidadeCartao(cartaoDto.getNumero(), cartaoDto.getBandeira());

    LocalDate data = LocalDate.parse(cartaoDto.getDataExpiracao(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    Cartao cartao = Cartao.builder().numero(cartaoDto.getNumero()).dataExpiracao(data)
        .tipo(cartaoDto.getTipo())
        .bandeira(cartaoDto.getBandeira()).cliente(cliente).build();

    cartaoRepository.save(cartao);
    return CartaoDto.map(cartao);
  }

  private void validarDuplicidadeCartao(String numero, Bandeira bandeira) {
    cartaoRepository.findByNumeroAndBandeira(numero, bandeira)
        .ifPresent(cartao -> {
          throw new DuplicatedException(ValidationMessage.NUMERO_BANDEIRA_CARTAO_JA_CADASTRADO);
        });
  }
}
