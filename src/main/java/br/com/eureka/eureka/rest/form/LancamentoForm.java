package br.com.eureka.eureka.rest.form;

import lombok.Data;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class LancamentoForm {
    @NotNull(message = "O campo 'lancamentoInvalido' é obrigatório.")
    private boolean lancamentoInvalido;

    private int numeroLancamento;

    @NotEmpty
    @NotBlank(message = "É essencial uma descrição preenchida.")
    @Size(max = 255)
    private String descricao;

    @NotNull(message = "A Data de Lançamento é um campo obrigatório.")
    @FutureOrPresent(message = "A Data de Lançamento deve ser atual ou futura.")
    private LocalDate dataLancamento;

    private Integer idLancamentoPai;

    @NotNull(message = "O Valor é um campo obrigatório.")
    @Positive(message = "O Valor do Lançamento deve ser maior que zero.")
    private BigDecimal valor;

    private Integer idTipoLancamento;
    private Integer idUnidade;
    private Integer idUnidadeOrcamentaria;
    private Integer idPrograma;
    private Integer idAcao;
    private Integer idFonteRecurso;
    private Integer idGrupoDespesa;
    private Integer idModalidadeAplicacao;
    private Integer idElementoDespesa;
    private Integer idSolicitante;
    private Integer idObjetivoEstrategico;
    private Integer idTipoTransacao;
    private String ged;

    @Size(max = 255)
    private String contratado;

    private Short anoeureka;
}
