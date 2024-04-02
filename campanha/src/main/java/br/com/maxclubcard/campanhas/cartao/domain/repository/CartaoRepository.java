package br.com.maxclubcard.campanhas.cartao.domain.repository;

import br.com.maxclubcard.campanhas.cartao.domain.Bandeira;
import br.com.maxclubcard.campanhas.cartao.domain.Cartao;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartaoRepository extends JpaRepository<Cartao, Long> {

  Optional<Cartao> findByNumeroAndBandeira(String numero, Bandeira bandeira);
}
