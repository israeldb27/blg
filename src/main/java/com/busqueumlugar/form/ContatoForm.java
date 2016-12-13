package com.busqueumlugar.form;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.busqueumlugar.model.Contato;
import com.busqueumlugar.model.Usuario;

public class ContatoForm extends BaseForm {
	
	private Long id;    
    private long idUsuarioHost;    
    private long idUsuarioConvidado;    
    private String status;    
    private String perfilContato; // padrao, corretor, imobiliaria
    private Date dataConvite; 
    
    private String opcaoOrdenacao = "";
    private String tipoLista = "";
    private String statusLeitura;       
    private String opcaoFiltro = "";
    

	public String getOpcaoFiltro() {
		return opcaoFiltro;
	}

	public void setOpcaoFiltro(String opcaoFiltro) {
		this.opcaoFiltro = opcaoFiltro;
	}	

	public String getStatusLeitura() {
		return statusLeitura;
	}

	public void setStatusLeitura(String statusLeitura) {
		this.statusLeitura = statusLeitura;
	}    
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public long getIdUsuarioHost() {
		return idUsuarioHost;
	}
	public void setIdUsuarioHost(long idUsuarioHost) {
		this.idUsuarioHost = idUsuarioHost;
	}
	public long getIdUsuarioConvidado() {
		return idUsuarioConvidado;
	}
	public void setIdUsuarioConvidado(long idUsuarioConvidado) {
		this.idUsuarioConvidado = idUsuarioConvidado;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPerfilContato() {
		return perfilContato;
	}
	public void setPerfilContato(String perfilContato) {
		this.perfilContato = perfilContato;
	}	
	public Date getDataConvite() {
		return dataConvite;
	}
	public void setDataConvite(Date dataConvite) {
		this.dataConvite = dataConvite;
	}
	public String getOpcaoOrdenacao() {
		return opcaoOrdenacao;
	}
	public void setOpcaoOrdenacao(String opcaoOrdenacao) {
		this.opcaoOrdenacao = opcaoOrdenacao;
	}
	public String getTipoLista() {
		return tipoLista;
	}
	public void setTipoLista(String tipoLista) {
		this.tipoLista = tipoLista;
	}

}
