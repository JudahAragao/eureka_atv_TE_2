package br.com.eureka.eureka.rest.dto;

import lombok.Data;

import java.util.Date;

@Data
public class LancamentoDto {
    private String contratado;
    private String descricao;
    private String dsAcao;
    private String dsFonteRecurso;
    private String dsGrupoDespesa;
    private String dsModalidadeAplicacao;
    private String dsObjetivoEstrategico;
    private String dsPrograma;
    private String dsSolicitante;
    private String dsTipoLancamento;
    private String dsTipoTransacao;
    private String dsUnidade;
    private String dsUnidadeOrcamentaria;
    private Date dataLancamento;
    private String GED;
    private int id;
    private int idLancamentoPai;
    private int numeroLancamento;
    private Boolean lancamentoInvalido;
    private float valor;
}
