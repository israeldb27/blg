package com.busqueumlugar.form;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.busqueumlugar.model.Imovel;
import com.busqueumlugar.model.Usuario;

public class AtividadesForm extends BaseForm {

	 private Long id;  
	 private Imovel imovel;
	 private Usuario usuario;	
	 private Date dataAtividade;  
	 private String descricao;
	 private String status; // criado, cancelado, incompleto, completo
	 private Date dataUltimaAtualizacao;
	 private String tipoAtividade;
	 
	 
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Imovel getImovel() {
		return imovel;
	}
	public void setImovel(Imovel imovel) {
		this.imovel = imovel;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public Date getDataAtividade() {
		return dataAtividade;
	}
	public void setDataAtividade(Date dataAtividade) {
		this.dataAtividade = dataAtividade;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getDataUltimaAtualizacao() {
		return dataUltimaAtualizacao;
	}
	public void setDataUltimaAtualizacao(Date dataUltimaAtualizacao) {
		this.dataUltimaAtualizacao = dataUltimaAtualizacao;
	}	
	
	public String getTipoAtividade() {
		return tipoAtividade;
	}
	public void setTipoAtividade(String tipoAtividade) {
		this.tipoAtividade = tipoAtividade;
	}
	    
}
