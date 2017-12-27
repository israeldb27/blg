package com.busqueumlugar.model;

import java.io.Serializable;
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

import com.busqueumlugar.enumerador.StatusAtividadesEnum;
import com.busqueumlugar.enumerador.TipoImovelEnum;

@Entity
@Table(name = "atividades")
public class Atividades extends BaseEntity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7168495997903705689L;

	@Id
    @GeneratedValue    
    @Column(name = "id")
    private Long id;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="idImovel")    
    private Imovel imovel;
    
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="idUsuario")    
    private Usuario usuario;
	
    @Column(name = "dataAtividade")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataAtividade;  
    
    @Column(name = "descricao")
    private String descricao;
    
    @Column(name = "status")
    private String status; // criado, cancelado, incompleto, completo    
    
    @Column(name = "dataUltimaAtualizacao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataUltimaAtualizacao;
    
    @Column(name = "tipoAtividade")
    private String tipoAtividade;
    
    @Transient
    private String statusFmt = "";
    


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


	public String getStatusFmt() {
		return StatusAtividadesEnum.getLabel(this.status);
	}


	public void setStatusFmt(String statusFmt) {
		this.statusFmt = statusFmt;
	}
	
	public String getTipoAtividade() {
		return tipoAtividade;
	}
	public void setTipoAtividade(String tipoAtividade) {
		this.tipoAtividade = tipoAtividade;
	}
    
    
	
	

}
