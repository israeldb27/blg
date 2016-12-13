package com.busqueumlugar.model;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.busqueumlugar.enumerador.AcaoImovelEnum;
import com.busqueumlugar.enumerador.TipoImovelEnum;


@Entity
@Table(name = "imovelcompartilhado")
public class Imovelcompartilhado implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)    
    @Column(name = "id")
    private Long id;    
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="idImovel")    
    private Imovel imovel;
    
	@ManyToOne
    @JoinColumn(name="idDonoImovel")    
    private Usuario usuarioDonoImovel;
	
	@ManyToOne
    @JoinColumn(name="idUsuarioSolicitante")    
    private Usuario usuarioSolicitante;
        
    @Column(name = "dataSolicitacao")
    @Temporal(TemporalType.DATE)
    private Date dataSolicitacao;
    
    @Column(name = "dataResposta")
    @Temporal(TemporalType.DATE)
    private Date dataResposta;    
    
    @Column(name = "status")
    private String status;   
    
    @Column(name = "statusLeitura")
    private String statusLeitura;        
        
    @Column(name = "descricaoCompartilhamento")
    private String descricaoCompartilhamento;
        
    @Column(name = "tipoImovelCompartilhado")
    private String tipoImovelCompartilhado; // {I - intermediação ; P - parceria }
     
    @Transient
    private int quantTotalParceiros;
	

	public Imovelcompartilhado() {
    }

    public Imovelcompartilhado(Long id) {
        this.id = id;
    }

    public Imovelcompartilhado(Long id, long idImovel, long idDonoImovel, long idUsuarioSolicitante, Date dataSolicitacao, String status, String usuarioSolicitante) {
        this.id = id;
        this.dataSolicitacao = dataSolicitacao;
        this.status = status;
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

    public Date getDataResposta() {
        return dataResposta;
    }

    public void setDataResposta(Date dataResposta) {
        this.dataResposta = dataResposta;
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
        if (!(object instanceof Imovelcompartilhado)) {
            return false;
        }
        Imovelcompartilhado other = (Imovelcompartilhado) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.app.imovelcompartilhado.Imovelcompartilhado[ id=" + id + " ]";
    }
 

    /**
     * @return the statusLeitura
     */
    public String getStatusLeitura() {
        return statusLeitura;
    }

    /**
     * @param statusLeitura the statusLeitura to set
     */
    public void setStatusLeitura(String statusLeitura) {
        this.statusLeitura = statusLeitura;
    }
   

    /**
     * @return the descricaoCompartilhamento
     */
    public String getDescricaoCompartilhamento() {
        return descricaoCompartilhamento;
    }

    /**
     * @param descricaoCompartilhamento the descricaoCompartilhamento to set
     */
    public void setDescricaoCompartilhamento(String descricaoCompartilhamento) {
        this.descricaoCompartilhamento = descricaoCompartilhamento;
    }

    /**
     * @return the tipoImovelCompartilhado
     */
    public String getTipoImovelCompartilhado() {
        return tipoImovelCompartilhado;
    }

    /**
     * @param tipoImovelCompartilhado the tipoImovelCompartilhado to set
     */
    public void setTipoImovelCompartilhado(String tipoImovelCompartilhado) {
        this.tipoImovelCompartilhado = tipoImovelCompartilhado;
    }

    
	public int getQuantTotalParceiros() {
		return quantTotalParceiros;
	}

	public void setQuantTotalParceiros(int quantTotalParceiros) {
		this.quantTotalParceiros = quantTotalParceiros;
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

	public Usuario getUsuarioSolicitante() {
		return usuarioSolicitante;
	}

	public void setUsuarioSolicitante(Usuario usuarioSolicitante) {
		this.usuarioSolicitante = usuarioSolicitante;
	}	
	
    
}
