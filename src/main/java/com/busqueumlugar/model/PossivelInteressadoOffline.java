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

import com.busqueumlugar.enumerador.chanceInteresseEnum;

@Entity
@Table(name = "PossivelInteressadooffline")
public class PossivelInteressadoOffline extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7890016360853629377L;
	
	@Id
    @GeneratedValue    
    @Column(name = "id")
    private Long id;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="idImovel")    
    private Imovel imovel;    
    
    @Column(name = "observacao")
    private String observacao;
    
    @Column(name = "dataCadastro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCadastro; 
    
    @Column(name = "porcentagemChanceInteresse")
    private Integer porcentagemChanceInteresse;
    
    @Column(name = "chanceInteresse")
    private String chanceInteresse; // /muito Baixa, Baixa, Media, Alta, Muito Alta
    
    @Column(name = "nomeInteressado")
    private String nomeInteressado;
    
    @Column(name = "emailInteressado")
    private String emailInteressado;
    
    @Column(name = "telefoneInteressado")
    private String telefoneInteressado;
    
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

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
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

	public String getchanceInteresse() {
		return chanceInteresse;
	}

	public void setchanceInteresse(String chanceInteresse) {
		this.chanceInteresse = chanceInteresse;
	}

	public String getnomeInteressado() {
		return nomeInteressado;
	}

	public void setnomeInteressado(String nomeInteressado) {
		this.nomeInteressado = nomeInteressado;
	}

	public String getemailInteressado() {
		return emailInteressado;
	}

	public void setemailInteressado(String emailInteressado) {
		this.emailInteressado = emailInteressado;
	}

	public String gettelefoneInteressado() {
		return telefoneInteressado;
	}

	public void settelefoneInteressado(String telefoneInteressado) {
		this.telefoneInteressado = telefoneInteressado;
	}

	public Date getDataUltimaAtualizacao() {
		return dataUltimaAtualizacao;
	}

	public void setDataUltimaAtualizacao(Date dataUltimaAtualizacao) {
		this.dataUltimaAtualizacao = dataUltimaAtualizacao;
	}

	public String getChanceInteresseFmt() {
		return chanceInteresseEnum.getLabel(this.chanceInteresse);
	}	
		
	public void setChanceInteresseFmt(String chanceInteresseFmt) {
		this.chanceInteresseFmt = chanceInteresseFmt;
	}
    
    

}
