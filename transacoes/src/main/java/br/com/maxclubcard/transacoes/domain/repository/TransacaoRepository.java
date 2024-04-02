package br.com.maxclubcard.transacoes.domain.repository;

import br.com.maxclubcard.transacoes.domain.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
}
