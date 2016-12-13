/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.busqueumlugar.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

import com.busqueumlugar.util.DateUtil;


@Entity
@Table(name = "paramservico")
public class Paramservico implements Serializable {
    
	private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)    
    @Column(name = "id")
    private Long id;    
    
    @Column(name = "labelServico")
    private String labelServico;    
    
    @Column(name = "valueServico")
    private String valueServico;
    
    @Column(name = "descricao")
    private String descricao;    
    
    @Column(name = "dataCriacao")
    @Temporal(TemporalType.DATE)
    private Date dataCriacao;
    
    @Column(name = "statusServico")
    private String statusServico;  
    
    @Column(name = "tipoParamServico")
    private String tipoParamServico;
    
    @Column(name = "cobranca")
    private String cobranca;   // indica se o servico será cobrado ou não    
    
    @Transient
    private String dataCriacaoFmt;

    public Paramservico() {
    }

    public Paramservico(Long id) {
        this.id = id;
    }

    public Paramservico(Long id, String labelServico, String valueServico, Date dataCriacao) {
        this.id = id;
        this.labelServico = labelServico;
        this.valueServico = valueServico;
        this.dataCriacao = dataCriacao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabelServico() {
        return labelServico;
    }

    public void setLabelServico(String labelServico) {
        this.labelServico = labelServico;
    }

    public String getValueServico() {
        return valueServico;
    }

    public void setValueServico(String valueServico) {
        this.valueServico = valueServico;
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

    public String getStatusServico() {
        return statusServico;
    }

    public void setStatusServico(String statusServico) {
        this.statusServico = statusServico;
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
        if (!(object instanceof Paramservico)) {
            return false;
        }
        Paramservico other = (Paramservico) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.app.paramServico.Paramservico[ id=" + id + " ]";
    }

    /**
     * @return the tipoParamServico
     */
    public String getTipoParamServico() {
        return tipoParamServico;
    }

    /**
     * @param tipoParamServico the tipoParamServico to set
     */
    public void setTipoParamServico(String tipoParamServico) {
        this.tipoParamServico = tipoParamServico;
    }

    /**
     * @return the cobranca
     */
    public String getCobranca() {
        return cobranca;
    }

    /**
     * @param cobranca the cobranca to set
     */
    public void setCobranca(String cobranca) {
        this.cobranca = cobranca;
    }

	public String getDataCriacaoFmt() {
		if ( dataCriacao != null)
			return DateUtil.formataData(this.dataCriacao);
		else
			return dataCriacaoFmt;
	}

	public void setDataCriacaoFmt(String dataCriacaoFmt) {
		this.dataCriacaoFmt = dataCriacaoFmt;
	}
    
}
