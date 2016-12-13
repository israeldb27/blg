package com.busqueumlugar.form;

import java.io.Serializable;
import java.util.Date;

public class RecomendacaoForm extends BaseForm implements Serializable{ 

	private static final long serialVersionUID = 1091555040483212254L;
	
	private Long id;
    private Long idUsuarioRecomendado;
    private Long idUsuario;  
    private String descricao;
    private Date dataRecomendacao;
    private Date dataResposta;
    private String status;
    private String statusLeitura;
    private String opcaoOrdenacao;
    
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getIdUsuarioRecomendado() {
		return idUsuarioRecomendado;
	}
	public void setIdUsuarioRecomendado(Long idUsuarioRecomendado) {
		this.idUsuarioRecomendado = idUsuarioRecomendado;
	}
	public Long getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Date getDataRecomendacao() {
		return dataRecomendacao;
	}
	public void setDataRecomendacao(Date dataRecomendacao) {
		this.dataRecomendacao = dataRecomendacao;
	}
	public Date getDataResposta() {
		return dataResposta;
	}
	public void setDataResposta(Date dataResposta) {
		this.dataResposta = dataResposta;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatusLeitura() {
		return statusLeitura;
	}
	public void setStatusLeitura(String statusLeitura) {
		this.statusLeitura = statusLeitura;
	}
	public String getOpcaoOrdenacao() {
		return opcaoOrdenacao;
	}
	public void setOpcaoOrdenacao(String opcaoOrdenacao) {
		this.opcaoOrdenacao = opcaoOrdenacao;
	}

}
