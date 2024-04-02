package br.com.maxclubcard.campanhas.cartao.domain.repository;

import br.com.maxclubcard.campanhas.cartao.domain.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartaoRepository extends JpaRepository<Cartao, Long> {

}
