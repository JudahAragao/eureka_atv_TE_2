package br.com.eureka.eureka.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "tbElementoDespesa")
public class ElementoDespesa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // Identificador único para Elemento de Despesa

    @Column(name = "codigo", nullable = false, unique = true)
    @NotNull(message = "O campo 'codigo' não pode ser nulo")
    private Integer codigo; // Código do Elemento de Despesa

    @Column(name = "nome", length = 255, nullable = false)
    private String nome; // Nome do Elemento de Despesa

    @Column(name = "dataCadastro", nullable = false)
    private LocalDateTime dataCadastro; // Data de cadastro do Elemento de Despesa

    @Column(name = "dataAlteracao")
    private LocalDateTime dataAlteracao; // Data da última alteração do Elemento de Despesa
}
