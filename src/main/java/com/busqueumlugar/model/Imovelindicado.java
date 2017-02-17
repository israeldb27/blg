package com.busqueumlugar.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name = "imovelindicado")
public class Imovelindicado implements Serializable {
		
	private static final long serialVersionUID = 7596120457560134033L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)    
    @Column(name = "id")
 	private Long id;	
    
    @Column(name = "observacao")
    private String observacao;    
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="idImovel")    
    private Imovel imovel;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="idUsuario")    
    private Usuario usuario;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="idUsuarioIndicador")    
    private Usuario usuarioIndicador;
    
    @Column(name = "statusIndicado")
    private String statusIndicado;    
    
    @Column(name = "dataIndicacao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataIndicacao; // criar este campo no banco ainda    
    
    @Column(name = "indicacaoEmail")
    private String indicacaoEmail; // S, N
    
    @Column(name = "emailImovelIndicado")
    private String emailImovelIndicado;
    
    @Transient
    private String interessadoImovel = "";   
    
	public String getInteressadoImovel() {
		return interessadoImovel;
	}

	public void setInteressadoImovel(String interessadoImovel) {
		this.interessadoImovel = interessadoImovel;
	}
	
	public Imovelindicado() {
    }

    public Imovelindicado(Long id) {
        this.id = id;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
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
        if (!(object instanceof Imovelindicado)) {
            return false;
        }
        Imovelindicado other = (Imovelindicado) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.app.imovelindicado.Imovelindicado[ id=" + id + " ]";
    }

  
    /**
     * @return the statusIndicado
     */
    public String getStatusIndicado() {
        return statusIndicado;
    }

    /**
     * @param statusIndicado the statusIndicado to set
     */
    public void setStatusIndicado(String statusIndicado) {
        this.statusIndicado = statusIndicado;
    }

    /**
     * @return the dataIndicacao
     */
    public Date getDataIndicacao() {
        return dataIndicacao;
    }

    /**
     * @param dataIndicacao the dataIndicacao to set
     */
    public void setDataIndicacao(Date dataIndicacao) {
        this.dataIndicacao = dataIndicacao;
    }

    /**
     * @return the indicacaoEmail
     */
    public String getIndicacaoEmail() {
        return indicacaoEmail;
    }

    /**
     * @param indicacaoEmail the indicacaoEmail to set
     */
    public void setIndicacaoEmail(String indicacaoEmail) {
        this.indicacaoEmail = indicacaoEmail;
    }

    /**
     * @return the emailImovelIndicado
     */
    public String getEmailImovelIndicado() {
        return emailImovelIndicado;
    }

    /**
     * @param emailImovelIndicado the emailImovelIndicado to set
     */
    public void setEmailImovelIndicado(String emailImovelIndicado) {
        this.emailImovelIndicado = emailImovelIndicado;
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

	public Usuario getUsuarioIndicador() {
		return usuarioIndicador;
	}

	public void setUsuarioIndicador(Usuario usuarioIndicador) {
		this.usuarioIndicador = usuarioIndicador;
	}
	
}
