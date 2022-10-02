package maia.goncalves.david.sacola.repository;

import maia.goncalves.david.sacola.model.Sacola;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SacolaRepository extends JpaRepository<Sacola, Long> {
}
