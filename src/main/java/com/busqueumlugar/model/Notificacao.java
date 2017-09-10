/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.busqueumlugar.model;

import java.io.Serializable;

import java.util.Date;

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


@Entity
@Table(name = "notificacao")
public class Notificacao implements Serializable {
   
	private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)    
    @Column(name = "id")
    private Long id;  
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="idImovel")    
    private Imovel imovel;
    
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="idUsuario")    
    private Usuario usuario;    
    
    @Column(name = "statusLeitura")
    private String statusLeitura;    
    
    @Column(name = "acao")
    private String acao;    
        
    @Column(name = "dataNotificacao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataNotificacao;
    
    @Column(name = "observacao")
    private String observacao;
    
    @Column(name = "descricao")
    private String descricao;
    
    @Column(name = "tipoNotificacao")
    private String tipoNotificacao;   // C - convite,  I - imovel,  S - servico, P - plano, U -usuario  (usado apenas para notificar que o usuario fez o cadastro)
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="idUsuarioConvite")    
    private Usuario usuarioConvite;
    
	@Transient
    private String imagemUsuario;    
   
    @Transient
    private String imagemImovel;
    
    
    public String getImagemImovel() {
		return imagemImovel;
	}

	public void setImagemImovel(String imagemImovel) {
		this.imagemImovel = imagemImovel;
	}

	public String getImagemUsuario() {
		return imagemUsuario;
	}

	public void setImagemUsuario(String imagemUsuario) {
		this.imagemUsuario = imagemUsuario;
	}

	public String getTipoNotificacao() {
		return tipoNotificacao;
	}

	public void setTipoNotificacao(String tipoNotificacao) {
		this.tipoNotificacao = tipoNotificacao;
	}

	public Notificacao() {
    }

    public Notificacao(Long id) {
        this.id = id;
    }

    public Notificacao(Long id, long idUsuario, String statusLeitura, String acao, Date dataNotificacao) {
        this.id = id;
        this.statusLeitura = statusLeitura;
        this.acao = acao;
        this.dataNotificacao = dataNotificacao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatusLeitura() {
        return statusLeitura;
    }

    public void setStatusLeitura(String statusLeitura) {
        this.statusLeitura = statusLeitura;
    }

    public String getAcao() {
        return acao;
    }

    public void setAcao(String acao) {
        this.acao = acao;
    }

    public Date getDataNotificacao() {
        return dataNotificacao;
    }

    public void setDataNotificacao(Date dataNotificacao) {
        this.dataNotificacao = dataNotificacao;
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
        if (!(object instanceof Notificacao)) {
            return false;
        }
        Notificacao other = (Notificacao) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.app.notificacao.Notificacao[ id=" + id + " ]";
    }

    /**
     * @return the descricao
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * @param descricao the descricao to set
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
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

	public Usuario getUsuarioConvite() {
		return usuarioConvite;
	}

	public void setUsuarioConvite(Usuario usuarioConvite) {
		this.usuarioConvite = usuarioConvite;
	}

    
}
