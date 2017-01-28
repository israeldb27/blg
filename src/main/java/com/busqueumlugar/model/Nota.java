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


@Entity
@Table(name = "nota")
public class Nota implements Serializable {
	
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue    
    @Column(name = "id")
    private Long id;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="idImovel")    
    private Imovel imovel;
    
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="idUsuario")    
    private Usuario usuario;
   
    @Column(name = "dataNota")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataNota;    
   
    @Column(name = "descricao")
    private String descricao;
       
    @Column(name = "acao")
    private String acao;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="idPreferenciaLocal")    
    private Preferencialocalidade prefLocal;

    public Nota() {
    }

    public Nota(Long id) {
        this.id = id;
    }

    public Nota(Long id, Date dataNota) {
        this.id = id;
        this.dataNota = dataNota;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Date getDataNota() {
        return dataNota;
    }

    public void setDataNota(Date dataNota) {
        this.dataNota = dataNota;
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
        if (!(object instanceof Nota)) {
            return false;
        }
        Nota other = (Nota) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.app.nota.Nota[ id=" + id + " ]";
    }

  

   

    /**
     * @return the acao
     */
    public String getAcao() {
        return acao;
    }

    /**
     * @param acao the acao to set
     */
    public void setAcao(String acao) {
        this.acao = acao;
    }
   

	public Imovel getImovel() {
		return imovel;
	}

	public void setImovel(Imovel imovel) {
		this.imovel = imovel;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Preferencialocalidade getPrefLocal() {
		return prefLocal;
	}

	public void setPrefLocal(Preferencialocalidade prefLocal) {
		this.prefLocal = prefLocal;
	}
    
}
