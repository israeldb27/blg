package com.busqueumlugar.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;


@Entity
@Table(name = "itemmensagemadmin")
public class ItemMensagemAdmin {
	
	@Id
    @GeneratedValue
    @Column(name = "id")
 	private Long id;
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="idMensagemAdmin")    
    private MensagemAdmin mensagemAdmin;
	
	@Column(name = "dataMensagem")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataMensagem; 	
	
	@Column(name = "descricao")
    private String descricao;
	
	@Column(name = "remetenteAdmin")
    private String remetenteAdmin; // S, N
	
	@Column(name = "statusLeitura")
    private String statusLeitura; // novo, lido
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="idUsuario")    
    private Usuario usuario;


	public String getStatusLeitura() {
		return statusLeitura;
	}

	public void setStatusLeitura(String statusLeitura) {
		this.statusLeitura = statusLeitura;
	}

	public String getRemetenteAdmin() {
		return remetenteAdmin;
	}

	public void setRemetenteAdmin(String remetenteAdmin) {
		this.remetenteAdmin = remetenteAdmin;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public MensagemAdmin getMensagemAdmin() {
		return mensagemAdmin;
	}

	public void setMensagemAdmin(MensagemAdmin mensagemAdmin) {
		this.mensagemAdmin = mensagemAdmin;
	}

}
