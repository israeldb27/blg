/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.busqueumlugar.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.busqueumlugar.enumerador.AcaoImovelEnum;
import com.busqueumlugar.enumerador.TipoImovelEnum;

/**
 *
 * @author Israel
 */
@Entity
@Table(name = "imovelfavoritos")
public class Imovelfavoritos implements Serializable {
	
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)    
    @Column(name = "id")
    private Long id;    
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="idUsuario")    
    private Usuario usuario; 
     
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="idImovel")    
    private Imovel imovel;    
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="idDonoImovel")    
    private Usuario usuarioDonoImovel;
    
    @Column(name = "status")
    private String status; // nova, lida     
        
    @Column(name = "dataInteresse")
    @Temporal(TemporalType.DATE)
    private Date dataInteresse;
        	
	
    public Imovelfavoritos() {
    }

    public Imovelfavoritos(Long id) {
        this.id = id;
    }
    
	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        if (!(object instanceof Imovelfavoritos)) {
            return false;
        }
        Imovelfavoritos other = (Imovelfavoritos) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.app.imovel.imovelfavoritos.Imovelfavoritos[ id=" + id + " ]";
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    
    /**
     * @return the dataInteresse
     */
    public Date getDataInteresse() {
        return dataInteresse;
    }

    /**
     * @param dataInteresse the dataInteresse to set
     */
    public void setDataInteresse(Date dataInteresse) {
        this.dataInteresse = dataInteresse;
    }

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Imovel getImovel() {
		return imovel;
	}

	public void setImovel(Imovel imovel) {
		this.imovel = imovel;
	}

	public Usuario getUsuarioDonoImovel() {
		return usuarioDonoImovel;
	}

	public void setUsuarioDonoImovel(Usuario usuarioDonoImovel) {
		this.usuarioDonoImovel = usuarioDonoImovel;
	}
	
	
}
