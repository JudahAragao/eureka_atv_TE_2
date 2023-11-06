package br.com.eureka.eureka.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name="FonteRecurso")
public class FonteRecurso {

    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Codigo;

    @Column(name = "Nome", length = 100, nullable = false)
    private String Nome;

    @Column(name = "DataCadastro", nullable = false)
    private LocalDate DataCadastro;

    @Column(name = "DataAlteracao", nullable = false)
    private LocalDate DataAlteracao;

    public void setDataAlteracao(LocalDateTime now) {
    }

}
