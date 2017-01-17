package com.busqueumlugar.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Basic;
import javax.persistence.Entity;
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

import com.busqueumlugar.enumerador.AcaoImovelEnum;
import com.busqueumlugar.enumerador.TipoImovelEnum;
import com.busqueumlugar.util.AppUtil;


@Entity
@Table(name = "imovelpropostas")
public class ImovelPropostas implements Serializable {
	
	
	private static final long serialVersionUID = -8886832788165307440L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
    private Long id;	
        
    @Column(name = "valorProposta")
    private BigDecimal valorProposta;
    
	@Column(name = "dataCadastro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCadastro;    
  
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="idUsuarioLancador")    
    private Usuario usuarioLancador;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="idImovel")    
    private Imovel imovel;
    
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="idUsuarioReceptor")    
    private Usuario usuarioReceptor; 
    
    @Column(name = "descricao")
    private String descricao;
    
    @Column(name = "observacao")
    private String observacao;  
    
    @Column(name = "status")
    private String status; // nova, lida 
    
    @Transient
    private String interessadoImovel = ""; // este campo � usado para indicar se o usu�rio est� interessado ou nao no imovel	
    
	public String getInteressadoImovel() {
		return interessadoImovel;
	}

	public void setInteressadoImovel(String interessadoImovel) {
		this.interessadoImovel = interessadoImovel;
	}

	public ImovelPropostas() {
    }

    public ImovelPropostas(Long id) {
        this.id = id;
    }

    public ImovelPropostas(Long id, BigDecimal valorProposta, Date dataCadastro) {
        this.id = id;
        this.valorProposta = valorProposta;
        this.dataCadastro = dataCadastro;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getValorProposta() {
        return valorProposta;
    }

    public void setValorProposta(BigDecimal valorProposta) {
        this.valorProposta = valorProposta;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
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
        if (!(object instanceof ImovelPropostas)) {
            return false;
        }
        ImovelPropostas other = (ImovelPropostas) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.app.imovel.imovelPropostas.ImovelPropostas[ id=" + id + " ]";
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
  
	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	
	public Usuario getUsuarioLancador() {
		return usuarioLancador;
	}

	public void setUsuarioLancador(Usuario usuarioLancador) {
		this.usuarioLancador = usuarioLancador;
	}

	public Imovel getImovel() {
		return imovel;
	}

	public void setImovel(Imovel imovel) {
		this.imovel = imovel;
	}

	public Usuario getUsuarioReceptor() {
		return usuarioReceptor;
	}

	public void setUsuarioReceptor(Usuario usuarioReceptor) {
		this.usuarioReceptor = usuarioReceptor;
	}
	
		
	


}
