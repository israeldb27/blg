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
@Table(name = "possivelcomprador")
public class PossivelComprador extends BaseEntity implements Serializable {
	
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
    @JoinColumn(name="idUsuarioComprador")    
    private Usuario usuarioComprador;
	
    @Column(name = "dataCadastro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCadastro; 
    
    @Column(name = "porcentagemChanceCompra")
    private Integer porcentagemChanceCompra;
    
    @Column(name = "chanceCompra")
    private String chanceCompra; // /muito Baixa, Baixa, Media, Alta, Muito Alta -- criar um Enum para este campo
    
    @Column(name = "observacao")
    private String observacao;
    
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

	public Usuario getUsuarioComprador() {
		return usuarioComprador;
	}

	public void setUsuarioComprador(Usuario usuarioComprador) {
		this.usuarioComprador = usuarioComprador;
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

	public String getChanceCompraFmt() {
		return ChanceCompraEnum.getLabel(this.chanceCompra);
	}

	public void setChanceCompraFmt(String chanceCompraFmt) {
		this.chanceCompraFmt = chanceCompraFmt;
	}
   
    

}
