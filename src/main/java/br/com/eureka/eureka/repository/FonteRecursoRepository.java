package br.com.eureka.eureka.repository;

import br.com.eureka.eureka.model.FonteRecurso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FonteRecursoRepository extends JpaRepository<FonteRecurso, Long> {
    // Métodos personalizados do repositório podem ser adicionados aqui, se necessário
}
