/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.busqueumlugar.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Israel
 */
@Entity
@Table(name = "infoservico")
public class Infoservico implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue    
    @Column(name = "id")
    private Long id;    
        
    @Column(name = "idParamServico")
    private long idParamServico;    
    
    @Column(name = "labelDuracao")
    private String labelDuracao;    
        
    @Column(name = "valorServico")
    private double valorServico;    
        
    @Column(name = "tempoDuracao")
    private int tempoDuracao;    
        
    @Column(name = "itemInfoServico")
    private String itemInfoServico;    
        
    @Column(name = "quantidade")
    private int quantidade;
            
    @Column(name = "tipoInfoServico")
    private String tipoInfoServico;
      

    public Infoservico() {
    }

    public Infoservico(Long id) {
        this.id = id;
    }

    public Infoservico(Long id, long idParamServico, String labelDuracao, double valorServico, int tempoDuracao) {
        this.id = id;
        this.idParamServico = idParamServico;
        this.labelDuracao = labelDuracao;
        this.valorServico = valorServico;
        this.tempoDuracao = tempoDuracao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getIdParamServico() {
        return idParamServico;
    }

    public void setIdParamServico(long idParamServico) {
        this.idParamServico = idParamServico;
    }

    public String getLabelDuracao() {
        return labelDuracao;
    }

    public void setLabelDuracao(String labelDuracao) {
        this.labelDuracao = labelDuracao;
    }

    public double getValorServico() {
        return valorServico;
    }

    public void setValorServico(double valorServico) {
        this.valorServico = valorServico;
    }

    public int getTempoDuracao() {
        return tempoDuracao;
    }

    public void setTempoDuracao(int tempoDuracao) {
        this.tempoDuracao = tempoDuracao;
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
        if (!(object instanceof Infoservico)) {
            return false;
        }
        Infoservico other = (Infoservico) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.app.paramServico.Infoservico[ id=" + id + " ]";
    }

    /**
     * @return the itemInfoServico
     */
    public String getItemInfoServico() {
        return itemInfoServico;
    }

    /**
     * @param itemInfoServico the itemInfoServico to set
     */
    public void setItemInfoServico(String itemInfoServico) {
        this.itemInfoServico = itemInfoServico;
    }

    /**
     * @return the quantidade
     */
    public int getQuantidade() {
        return quantidade;
    }

    /**
     * @param quantidade the quantidade to set
     */
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    /**
     * @return the tipoInfoServico
     */
    public String getTipoInfoServico() {
        return tipoInfoServico;
    }

    /**
     * @param tipoInfoServico the tipoInfoServico to set
     */
    public void setTipoInfoServico(String tipoInfoServico) {
        this.tipoInfoServico = tipoInfoServico;
    }

    
}
