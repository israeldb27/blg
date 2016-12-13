package com.busqueumlugar.form;

import java.util.Date;

import javax.persistence.Column;



public class ItemMensagemAdminForm {
	
	private Long id;	
 	private long idMensagemAdmin;
 	private Date dataMensagem;
    private String descricao;
    private String remetenteAdmin;
    private String statusLeitura; 
    private long idUsuario;  

	public long getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(long idUsuario) {
		this.idUsuario = idUsuario;
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
	public long getIdMensagemAdmin() {
		return idMensagemAdmin;
	}
	public void setIdMensagemAdmin(long idMensagemAdmin) {
		this.idMensagemAdmin = idMensagemAdmin;
	}
	public Date getDataMensagem() {
		return dataMensagem;
	}
	public void setDataMensagem(Date dataMensagem) {
		this.dataMensagem = dataMensagem;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getRemetenteAdmin() {
		return remetenteAdmin;
	}
	public void setRemetenteAdmin(String remetenteAdmin) {
		this.remetenteAdmin = remetenteAdmin;
	}
    
    

}
