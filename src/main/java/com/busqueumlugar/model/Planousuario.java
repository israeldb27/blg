package com.busqueumlugar.model;

import java.io.Serializable;
import java.math.BigInteger;
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

import com.busqueumlugar.enumerador.PerfilUsuarioEnum;
import com.busqueumlugar.enumerador.StatusPagtoEnum;


@Entity
@Table(name = "planousuario")
public class Planousuario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)    
    @Column(name = "id")
    private Long id;    
        
    @ManyToOne
    @JoinColumn(name="idPlano")    
    private Plano plano;
    
    @ManyToOne
    @JoinColumn(name="idUsuario")    
    private Usuario usuario;   
        
    @Column(name = "dataSolicitacao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataSolicitacao;
    
    @Column(name = "dataAguardando")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataAguardando;    
    
    @Column(name = "dataPagto")
    @Temporal(TemporalType.DATE)
    private Date dataPagto;
    
    @Column(name = "status")
    private String status;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="idImovel")    
    private Imovel imovel;
    
    @Column(name = "formaPagto")
    private String formaPagto;
    
    @Column(name = "valorPlano")
    private double valorPlano;
    
    @Column(name = "nomePlano")
    private String nomePlano;

	
	@Transient
	private String statusFmt;
    
    
    public Date getDataAguardando() {
		return dataAguardando;
	}

	public void setDataAguardando(Date dataAguardando) {
		this.dataAguardando = dataAguardando;
	}


	public Planousuario() {
    }

    public Planousuario(Long id) {
        this.id = id;
    }

    public Planousuario(Long id, Date dataSolicitacao) {
        this.id = id;
        this.dataSolicitacao = dataSolicitacao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDataSolicitacao() {
        return dataSolicitacao;
    }

    public void setDataSolicitacao(Date dataSolicitacao) {
        this.dataSolicitacao = dataSolicitacao;
    }

    public Date getDataPagto() {
        return dataPagto;
    }

    public void setDataPagto(Date dataPagto) {
        this.dataPagto = dataPagto;
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
        if (!(object instanceof Planousuario)) {
            return false;
        }
        Planousuario other = (Planousuario) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.app.plano.Planousuario[ id=" + id + " ]";
    }

    /**
     * @return the formaPagto
     */
    public String getFormaPagto() {
        return formaPagto;
    }

    /**
     * @param formaPagto the formaPagto to set
     */
    public void setFormaPagto(String formaPagto) {
        this.formaPagto = formaPagto;
    }

    /**
     * @return the valorPlano
     */
    public double getValorPlano() {
        return valorPlano;
    }

    /**
     * @param valorPlano the valorPlano to set
     */
    public void setValorPlano(double valorPlano) {
        this.valorPlano = valorPlano;
    }
	
	public String getStatusFmt() {
		return StatusPagtoEnum.getLabel(this.status); 
	}

	public void setStatusFmt(String statusFmt) {
		this.statusFmt = statusFmt;
	}

	public Plano getPlano() {
		return plano;
	}

	public void setPlano(Plano plano) {
		this.plano = plano;
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

	public String getNomePlano() {
		return nomePlano;
	}

	public void setNomePlano(String nomePlano) {
		this.nomePlano = nomePlano;
	}
	
    
}
