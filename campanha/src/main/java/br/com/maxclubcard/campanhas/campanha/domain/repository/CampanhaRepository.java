package br.com.maxclubcard.campanhas.campanha.domain.repository;

import br.com.maxclubcard.campanhas.campanha.domain.Campanha;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CampanhaRepository extends JpaRepository<Campanha, Long> {

  List<Campanha> findByValorMinimoLessThanEqualAndClientes_Cpf(BigDecimal valor, String cpf);

  Optional<Campanha> findByNome(String nome);
}
