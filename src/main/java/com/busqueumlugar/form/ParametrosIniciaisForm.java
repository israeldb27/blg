package com.busqueumlugar.form;

import java.util.Date;

import com.busqueumlugar.util.MessageUtils;
import com.mysql.jdbc.StringUtils;

public class ParametrosIniciaisForm {
	
	private Long id;
    private String nome;
    private String label;
    private String tipoParam;
    private Long idUsuarioCadastro;
    private String valorHabilita;
    private Date dataCadastro;
    private Date dataUltimaAtualizacao;
    private Long valorQuantidade;
    private String observacao;
    private String tipoParamDesc;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getTipoParam() {
		return tipoParam;
	}
	public void setTipoParam(String tipoParam) {
		this.tipoParam = tipoParam;
	}
	public Long getIdUsuarioCadastro() {
		return idUsuarioCadastro;
	}
	public void setIdUsuarioCadastro(Long idUsuarioCadastro) {
		this.idUsuarioCadastro = idUsuarioCadastro;
	}
	public String getValorHabilita() {
		return valorHabilita;
	}
	public void setValorHabilita(String valorHabilita) {
		this.valorHabilita = valorHabilita;
	}
	public Date getDataCadastro() {
		return dataCadastro;
	}
	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
	public Date getDataUltimaAtualizacao() {
		return dataUltimaAtualizacao;
	}
	public void setDataUltimaAtualizacao(Date dataUltimaAtualizacao) {
		this.dataUltimaAtualizacao = dataUltimaAtualizacao;
	}
	public Long getValorQuantidade() {
		return valorQuantidade;
	}
	public void setValorQuantidade(Long valorQuantidade) {
		this.valorQuantidade = valorQuantidade;
	}
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	public String getTipoParamDesc() {
		if (! StringUtils.isNullOrEmpty(tipoParam)){
			if ( (this.tipoParam.equals("N")))
				return MessageUtils.getMessage("lbl.admin.param.iniciais.tipo.numerico");
			else if ( (this.tipoParam.equals("C")))
				return MessageUtils.getMessage("lbl.admin.param.iniciais.tipo.caractere");
		}
		return tipoParamDesc;
	}
	public void setTipoParamDesc(String tipoParamDesc) {
		this.tipoParamDesc = tipoParamDesc;
	}

}
