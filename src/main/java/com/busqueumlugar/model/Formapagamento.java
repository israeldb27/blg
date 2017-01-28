/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.busqueumlugar.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.busqueumlugar.util.DateUtil;


/**
 *
 * @author Israel
 */
@Entity
@Table(name = "formapagamento")    
public class Formapagamento implements Serializable {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = -1615733004632098605L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)    
    @Column(name = "id")
    private Long id;    
    
    @Column(name = "nome")
    private String nome;
    
    @Column(name = "descricao")
    private String descricao;    
        
    @Column(name = "dataCriacao")
    @Temporal(TemporalType.DATE)
    private Date dataCriacao;    
    
    @Column(name = "label")
    private String label;
    
    @Column(name = "taxaValor")
    private Double taxaValor;
    
    @Transient
    private String dataCriacaoFmt;

    public Formapagamento() {
    }

    public Formapagamento(Long id) {
        this.id = id;
    }

    public Formapagamento(Long id, String nome, Date dataCriacao) {
        this.id = id;
        this.nome = nome;
        this.dataCriacao = dataCriacao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Formapagamento)) {
            return false;
        }
        Formapagamento other = (Formapagamento) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.app.servico.Formapagamento[ id=" + id + " ]";
    }

    /**
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    /**
     * @param label the label to set
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * @return the taxaValor
     */
    public Double getTaxaValor() {
        return taxaValor;
    }

    /**
     * @param taxaValor the taxaValor to set
     */
    public void setTaxaValor(Double taxaValor) {
        this.taxaValor = taxaValor;
    }

	public String getDataCriacaoFmt() {
		if ( this.dataCriacao != null)
			return DateUtil.formataData(dataCriacao);
		else
			return dataCriacaoFmt;	
	}

	public void setDataCriacaoFmt(String dataCriacaoFmt) {
		this.dataCriacaoFmt = dataCriacaoFmt;
	}
    
}
