package br.com.maxclubcard.campanhas.sorteio.domain.repository;

import br.com.maxclubcard.campanhas.sorteio.domain.SorteioParticipante;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SorteioParticipanteRepository extends JpaRepository<SorteioParticipante, Long> {

  @Query(value = "SELECT sp FROM SorteioParticipante sp WHERE sp.campanha.id = :campanhaId ORDER BY RAND()")
  List<SorteioParticipante> findRandomByCampanhaId(Long campanhaId);
}
