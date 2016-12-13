package com.busqueumlugar.form;

import java.util.Date;

import com.busqueumlugar.util.DateUtil;

/**
 *
 * @author Israel
 */
public class ParamservicoForm {
    
    private Long id;    
    private String labelServico = "";
    private String valueServico = "";
    private String descricao = "";
    private Date dataCriacao;
    private String statusServico = "";
    private String tipoParamServico = "";
    private String cobranca = "";
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
     * @return the labelServico
     */
    public String getLabelServico() {
        return labelServico;
    }

    /**
     * @param labelServico the labelServico to set
     */
    public void setLabelServico(String labelServico) {
        this.labelServico = labelServico;
    }

    /**
     * @return the valueServico
     */
    public String getValueServico() {
        return valueServico;
    }

    /**
     * @param valueServico the valueServico to set
     */
    public void setValueServico(String valueServico) {
        this.valueServico = valueServico;
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
     * @return the statusServico
     */
    public String getStatusServico() {
        return statusServico;
    }

    /**
     * @param statusServico the statusServico to set
     */
    public void setStatusServico(String statusServico) {
        this.statusServico = statusServico;
    }

    /**
     * @return the tipoParamServico
     */
    public String getTipoParamServico() {
        return tipoParamServico;
    }

    /**
     * @param tipoParamServico the tipoParamServico to set
     */
    public void setTipoParamServico(String tipoParamServico) {
        this.tipoParamServico = tipoParamServico;
    }

    /**
     * @return the cobranca
     */
    public String getCobranca() {
        return cobranca;
    }

    /**
     * @param cobranca the cobranca to set
     */
    public void setCobranca(String cobranca) {
        this.cobranca = cobranca;
    }

	public String getDataCriacaoFmt() {
		if ( this.dataCriacao != null)
			return DateUtil.formataData(this.dataCriacao);
		else
			return this.dataCriacaoFmt; 
	}

	public void setDataCriacaoFmt(String dataCriacaoFmt) {
		this.dataCriacaoFmt = dataCriacaoFmt;
	}
    

    
}
