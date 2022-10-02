package maia.goncalves.david.sacola.repository;


import maia.goncalves.david.sacola.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {


}
