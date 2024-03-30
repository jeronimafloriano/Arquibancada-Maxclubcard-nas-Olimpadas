package br.com.maxclubcard.cartao.domain.repository;

import br.com.maxclubcard.cartao.domain.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartaoRepository extends JpaRepository<Cartao, Long> {

}
