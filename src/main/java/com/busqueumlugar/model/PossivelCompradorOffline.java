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

import com.busqueumlugar.enumerador.ChanceCompraEnum;

@Entity
@Table(name = "possivelcompradoroffline")
public class PossivelCompradorOffline extends BaseEntity implements Serializable {

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
    
    @Column(name = "porcentagemChanceCompra")
    private Integer porcentagemChanceCompra;
    
    @Column(name = "chanceCompra")
    private String chanceCompra; // /muito Baixa, Baixa, Media, Alta, Muito Alta
    
    @Column(name = "nomeComprador")
    private String nomeComprador;
    
    @Column(name = "emailComprador")
    private String emailComprador;
    
    @Column(name = "telefoneComprador")
    private String telefoneComprador;
    
    @Column(name = "dataUltimaAtualizacao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataUltimaAtualizacao;
    
    @Transient
    private String chanceCompraFmt = "";

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

	public Integer getPorcentagemChanceCompra() {
		return porcentagemChanceCompra;
	}

	public void setPorcentagemChanceCompra(Integer porcentagemChanceCompra) {
		this.porcentagemChanceCompra = porcentagemChanceCompra;
	}

	public String getChanceCompra() {
		return chanceCompra;
	}

	public void setChanceCompra(String chanceCompra) {
		this.chanceCompra = chanceCompra;
	}

	public String getNomeComprador() {
		return nomeComprador;
	}

	public void setNomeComprador(String nomeComprador) {
		this.nomeComprador = nomeComprador;
	}

	public String getEmailComprador() {
		return emailComprador;
	}

	public void setEmailComprador(String emailComprador) {
		this.emailComprador = emailComprador;
	}

	public String getTelefoneComprador() {
		return telefoneComprador;
	}

	public void setTelefoneComprador(String telefoneComprador) {
		this.telefoneComprador = telefoneComprador;
	}

	public Date getDataUltimaAtualizacao() {
		return dataUltimaAtualizacao;
	}

	public void setDataUltimaAtualizacao(Date dataUltimaAtualizacao) {
		this.dataUltimaAtualizacao = dataUltimaAtualizacao;
	}

	public String getChanceCompraFmt() {
		return ChanceCompraEnum.getLabel(this.chanceCompra);
	}	
		
	public void setChanceCompraFmt(String chanceCompraFmt) {
		this.chanceCompraFmt = chanceCompraFmt;
	}
    
    

}
