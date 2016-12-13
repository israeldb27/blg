package com.busqueumlugar.model;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name = "mensagem")
public class Mensagem {
	
	@Id
    @GeneratedValue
    @Column(name = "id")
 	private Long id;	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idUsuarioDe")    
    private Usuario usuarioDe;
	    
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idUsuarioPara")    
    private Usuario usuarioPara;
    
    @Column(name = "descricao")
    private String descricao; 
        
    @Column(name = "dataMensagem")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataMensagem;    
    
  /*  @Column(name = "perfilUsuario")
    private String perfilUsuario; // campo usado apenas para mensagens    */
    
    @Column(name = "status")
    private String status; // novo, lido  


    public Mensagem() {
    }

    public Mensagem(Long id) {
        this.id = id;
    }

    public Mensagem(Long id, String descricao) {
        this.id = id;        
        this.descricao = descricao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
   
    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
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
        if (!(object instanceof Mensagem)) {
            return false;
        }
        Mensagem other = (Mensagem) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.app.mensagem.Mensagem[ id=" + id + " ]";
    }
   
    /**
     * @return the dataMensagem
     */
    public Date getDataMensagem() {
        return dataMensagem;
    }

    /**
     * @param dataMensagem the dataMensagem to set
     */
    public void setDataMensagem(Date dataMensagem) {
        this.dataMensagem = dataMensagem;
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

	public Usuario getUsuarioDe() {
		return usuarioDe;
	}

	public void setUsuarioDe(Usuario usuarioDe) {
		this.usuarioDe = usuarioDe;
	}

	public Usuario getUsuarioPara() {
		return usuarioPara;
	}

	public void setUsuarioPara(Usuario usuarioPara) {
		this.usuarioPara = usuarioPara;
	}

        

}
