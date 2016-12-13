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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;


@Entity
@Table(name = "contato")
public class Contato implements Serializable {
    private static final long serialVersionUID = 1L;    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)    
    @Column(name = "id")
    private Long id;    
        
	@ManyToOne
    @JoinColumn(name="idUsuarioHost")   
    private Usuario usuarioHost; 
	
	@ManyToOne
    @JoinColumn(name="idUsuarioConvidado")   
    private Usuario usuarioConvidado;	
   
    @Column(name = "status")
    private String status;    
    
    @Column(name = "perfilContato")
    private String perfilContato; // padrao, corretor, imobiliaria    
    
    @Column(name = "dataConvite")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataConvite;    
    
    @Column(name = "statusLeitura")
    private String statusLeitura;
    
    
	public Contato() {
    }

    public Contato(Long id) {
        this.id = id;
    }

    public Contato(Long id, String status) {
        this.id = id;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        if (!(object instanceof Contato)) {
            return false;
        }
        Contato other = (Contato) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.app.contato.Contato[ id=" + id + " ]";
    }

    /**
     * @return the perfilContato
     */
    public String getPerfilContato() {
        return perfilContato;
    }

    /**
     * @param perfilContato the perfilContato to set
     */
    public void setPerfilContato(String perfilContato) {
        this.perfilContato = perfilContato;
    }

 
    /**
     * @return the dataConvite
     */
    public Date getDataConvite() {
        return dataConvite;
    }

    /**
     * @param dataConvite the dataConvite to set
     */
    public void setDataConvite(Date dataConvite) {
        this.dataConvite = dataConvite;
    }
    
    public String getStatusLeitura() {
		return statusLeitura;
	}

	public void setStatusLeitura(String statusLeitura) {
		this.statusLeitura = statusLeitura;
	}

	public Usuario getUsuarioHost() {
		return usuarioHost;
	}

	public void setUsuarioHost(Usuario usuarioHost) {
		this.usuarioHost = usuarioHost;
	}

	public Usuario getUsuarioConvidado() {
		return usuarioConvidado;
	}

	public void setUsuarioConvidado(Usuario usuarioConvidado) {
		this.usuarioConvidado = usuarioConvidado;
	}
}
