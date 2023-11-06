package br.com.eureka.eureka.rest.form;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
public class LancamentoUpdateForm {
    private boolean lancamentoInvalido;
    private Integer numeroLancamento;
    
    @Size(max = 255)
    private String descricao;
    
    private LocalDate dataLancamento;
    private Integer idLancamentoPai;
    private Float valor;
    
    @NotNull
    private Integer idTipoLancamento;
    
    @NotNull
    private Integer idUnidade;
    
    @NotNull
    private Integer idUnidadeOrcamentaria;
    
    @NotNull
    private Integer idPrograma;
    
    @NotNull
    private Integer idAcao;
    
    @NotNull
    private Integer idFonteRecurso;
    
    @NotNull
    private Integer idGrupoDespesa;
    
    @NotNull
    private Integer idModalidadeAplicacao;
    
    @NotNull
    private Integer idElementoDespesa;
    
    private Integer idSolicitante;
    private Integer idObjetivoEstrategico;
    
    @NotNull
    private Integer idTipoTransacao;
    
    @Size(max = 255)
    private String ged;
    
    @Size(max = 255)
    private String contratado;
}
