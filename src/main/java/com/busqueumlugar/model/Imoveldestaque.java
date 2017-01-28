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
import javax.persistence.JoinColumn;

import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;



@Entity
@Table(name = "imoveldestaque")
public class Imoveldestaque implements Serializable {
	
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)    
    @Column(name = "id")
    private Long id;    
        
    @ManyToOne
    @JoinColumn(name="idImovel")    
    private Imovel imovel;    
        
    @Column(name = "dataDestaque")
    @Temporal(TemporalType.DATE)
    private Date dataDestaque;       
	
	@Column(name = "dataCadastro")
    @Temporal(TemporalType.DATE)
    private Date dataCadastro;  
        
    @Column(name = "status") // criado, cancelado, suspenso
    private String status;    
        
    @Column(name = "tipo")
    private String tipo; // anuncio, indicacao    	


    public Imoveldestaque() {
    }

    public Imoveldestaque(Long id) {
        this.id = id;
    }

    public Imoveldestaque(Long id, long idImovel, Date dataInicio, Date dataFim, String status) {
        this.id = id;          
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

  

    public Date getDataDestaque() {
        return dataDestaque;
    }

    public void setDataDestaque(Date dataDestaque) {
        this.dataDestaque = dataDestaque;
    }

	public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
        if (!(object instanceof Imoveldestaque)) {
            return false;
        }
        Imoveldestaque other = (Imoveldestaque) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.app.imoveldestaque.Imoveldestaque[ id=" + id + " ]";
    }

    /**
     * @return the tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

	public Imovel getImovel() {
		return imovel;
	}

	public void setImovel(Imovel imovel) {
		this.imovel = imovel;
	}
    
}
