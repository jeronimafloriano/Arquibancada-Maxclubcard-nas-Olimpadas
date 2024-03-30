package br.com.maxclubcard.campanha.domain.repository;

import br.com.maxclubcard.campanha.domain.Campanha;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CampanhaRepository extends JpaRepository<Campanha, Long> {
}
