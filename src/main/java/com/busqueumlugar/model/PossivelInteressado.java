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

import com.busqueumlugar.enumerador.ChanceInteresseEnum;


@Entity
@Table(name = "possivelinteressado")
public class PossivelInteressado extends BaseEntity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3309673892198150910L;

	@Id
    @GeneratedValue    
    @Column(name = "id")
    private Long id;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="idImovel")    
    private Imovel imovel;
    
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="idUsuarioInteressado")    
    private Usuario usuarioInteressado;
	
    @Column(name = "dataCadastro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCadastro; 
    
    @Column(name = "porcentagemChanceInteresse")
    private Integer porcentagemChanceInteresse;
    
    @Column(name = "chanceInteresse")
    private String chanceInteresse; // /muito Baixa, Baixa, Media, Alta, Muito Alta -- criar um Enum para este campo
    
    @Column(name = "observacao")
    private String observacao;
    
    @Column(name = "dataUltimaAtualizacao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataUltimaAtualizacao;
    
    @Transient
    private String chanceInteresseFmt = "";
    
    

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

	public Usuario getUsuarioInteressado() {
		return usuarioInteressado;
	}

	public void setUsuarioInteressado(Usuario usuarioInteressado) {
		this.usuarioInteressado = usuarioInteressado;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Integer getporcentagemChanceInteresse() {
		return porcentagemChanceInteresse;
	}

	public void setPorcentagemChanceInteresse(Integer porcentagemChanceInteresse) {
		this.porcentagemChanceInteresse = porcentagemChanceInteresse;
	}

	public String getChanceInteresse() {
		return chanceInteresse;
	}

	public void setChanceInteresse(String chanceInteresse) {
		this.chanceInteresse = chanceInteresse;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Date getDataUltimaAtualizacao() {
		return dataUltimaAtualizacao;
	}

	public void setDataUltimaAtualizacao(Date dataUltimaAtualizacao) {
		this.dataUltimaAtualizacao = dataUltimaAtualizacao;
	}

	public String getchanceInteresseFmt() {
		return ChanceInteresseEnum.getLabel(this.chanceInteresse);
	}

	public void setChanceInteresseFmt(String chanceInteresseFmt) {
		this.chanceInteresseFmt = chanceInteresseFmt;
	}
   
    

}
