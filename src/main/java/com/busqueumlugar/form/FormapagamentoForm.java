package com.busqueumlugar.form;

import java.util.Date;

import com.busqueumlugar.util.DateUtil;

/**
 *
 * @author Israel
 */
public class FormapagamentoForm {
    
    
    private Long id;    
    private String nome = ""; 
    private String descricao = "";
    private Date dataCriacao;
    private String label = "";
    private Double taxaValor = 0d;
    private String dataCriacaoFmt;

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the descricao
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * @param descricao the descricao to set
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /**
     * @return the dataCriacao
     */
    public Date getDataCriacao() {
        return dataCriacao;
    }

    /**
     * @param dataCriacao the dataCriacao to set
     */
    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    /**
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    /**
     * @param label the label to set
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * @return the taxaValor
     */
    public Double getTaxaValor() {
        return taxaValor;
    }

    /**
     * @param taxaValor the taxaValor to set
     */
    public void setTaxaValor(Double taxaValor) {
        this.taxaValor = taxaValor;
    }

	public String getDataCriacaoFmt() {
		if ( this.dataCriacao != null)
			return DateUtil.formataData(dataCriacao);
		else
			return dataCriacaoFmt;	
	}

	public void setDataCriacaoFmt(String dataCriacaoFmt) {
		this.dataCriacaoFmt = dataCriacaoFmt;
	}
    
}
