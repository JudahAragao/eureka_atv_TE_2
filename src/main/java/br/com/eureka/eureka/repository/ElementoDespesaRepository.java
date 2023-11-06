package br.com.eureka.eureka.repository;

import br.com.eureka.eureka.model.ElementoDespesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ElementoDespesaRepository extends JpaRepository<ElementoDespesa, Integer> {
}
