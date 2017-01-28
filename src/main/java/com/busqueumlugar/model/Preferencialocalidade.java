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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.busqueumlugar.enumerador.AcaoImovelEnum;
import com.busqueumlugar.enumerador.StatusImovelEnum;
import com.busqueumlugar.enumerador.TipoImovelEnum;
import com.mysql.jdbc.StringUtils;

/**
 *
 * @author Israel
 */
@Entity
@Table(name = "preferencialocalidade")    
public class Preferencialocalidade implements Serializable {
	
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue    
    @Column(name = "id")
    private Long id;    
        
    @Column(name = "idEstado")
    private int idEstado;    
        
    @Column(name = "idCidade")
    private int idCidade;    
        
    @Column(name = "idBairro")
    private int idBairro;    
        
    @ManyToOne
    @JoinColumn(name="idUsuario")    
    private Usuario usuario;   
    
    @Column(name = "nomeEstado")
    private String nomeEstado;    
            
    @Column(name = "nomeCidade")
    private String nomeCidade;    
        
    @Column(name = "nomeBairro")
    private String nomeBairro;    
    
    @Column(name = "tipoImovel")
    private String tipoImovel;    
    
    @Column(name = "acao")
    private String acao;
	
	@Column(name = "quantQuartos")
    private int quantQuartos;      
    
    @Column(name = "quantGaragem")
    private int quantGaragem;      
    
	@Column(name = "quantSuites")
    private int quantSuites;
	
	@Column(name = "quantBanheiro")
    private int quantBanheiro;
	
	@Column(name = "perfilImovel")
	private String perfilImovel;
	
	@Column(name = "estado")
	private String estado;
	
	@Column(name = "dataCadastro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCadastro; 
	
	@Transient
	private String tipoImovelFmt = "";
    
	@Transient
	private String acaoFmt = "";
	
	@Transient
	private String perfilImovelFmt = ""; 

    public Preferencialocalidade() {
    }

    public Preferencialocalidade(Long id) {
        this.id = id;
    }

    public Preferencialocalidade(Long id, int idEstado, int idCidade, int idBairro) {
        this.id = id;
        this.idEstado = idEstado;
        this.idCidade = idCidade;
        this.idBairro = idBairro;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
    }

    public int getIdCidade() {
        return idCidade;
    }

    public void setIdCidade(int idCidade) {
        this.idCidade = idCidade;
    }

    public int getIdBairro() {
        return idBairro;
    }

    public void setIdBairro(int idBairro) {
        this.idBairro = idBairro;
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
        if (!(object instanceof Preferencialocalidade)) {
            return false;
        }
        Preferencialocalidade other = (Preferencialocalidade) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.app.preferencias.Preferencialocalidade[ id=" + id + " ]";
    }

    /**
     * @return the nomeEstado
     */
    public String getNomeEstado() {
        return nomeEstado;
    }

    /**
     * @param nomeEstado the nomeEstado to set
     */
    public void setNomeEstado(String nomeEstado) {
        this.nomeEstado = nomeEstado;
    }

    /**
     * @return the nomeCidade
     */
    public String getNomeCidade() {
        return nomeCidade;
    }

    /**
     * @param nomeCidade the nomeCidade to set
     */
    public void setNomeCidade(String nomeCidade) {
        this.nomeCidade = nomeCidade;
    }

    /**
     * @return the nomeBairro
     */
    public String getNomeBairro() {
        return nomeBairro;
    }

    /**
     * @param nomeBairro the nomeBairro to set
     */
    public void setNomeBairro(String nomeBairro) {
        this.nomeBairro = nomeBairro;
    }

    /**
     * @return the tipoImovel
     */
    public String getTipoImovel() {
        return tipoImovel;
    }

    /**
     * @param tipoImovel the tipoImovel to set
     */
    public void setTipoImovel(String tipoImovel) {
        this.tipoImovel = tipoImovel;
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
	
	public String getTipoImovelFmt() {
		return TipoImovelEnum.getLabel(this.tipoImovel); 
	}

	public void setTipoImovelFmt(String tipoImovelFmt) {
		this.tipoImovelFmt = tipoImovelFmt;
	}
	
	public String getAcaoFmt() {
		return  AcaoImovelEnum.getLabel(this.acao);
	}

	public void setAcaoFmt(String acaoFmt) {
		this.acaoFmt = acaoFmt;
	}
	
	/**
     * @return the quantQuartos
     */
    public int getQuantQuartos() {
        return quantQuartos;
    }

    /**
     * @param quantQuartos the quantQuartos to set
     */
    public void setQuantQuartos(int quantQuartos) {
        this.quantQuartos = quantQuartos;
    }

    /**
     * @return the quantGaragem
     */
    public int getQuantGaragem() {
        return quantGaragem;
    }

    /**
     * @param quantGaragem the quantGaragem to set
     */
    public void setQuantGaragem(int quantGaragem) {
        this.quantGaragem = quantGaragem;
    }

    /**
     * @return the quantSuites
     */
    public int getQuantSuites() {
        return quantSuites;
    }

    /**
     * @param quantSuites the quantSuites to set
     */
    public void setQuantSuites(int quantSuites) {
        this.quantSuites = quantSuites;
    }

	public int getQuantBanheiro() {
		return quantBanheiro;
	}

	public void setQuantBanheiro(int quantBanheiro) {
		this.quantBanheiro = quantBanheiro;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getPerfilImovel() {
		return perfilImovel;
	}

	public void setPerfilImovel(String perfilImovel) {
		this.perfilImovel = perfilImovel;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getPerfilImovelFmt() {
		if (! StringUtils.isEmptyOrWhitespaceOnly(perfilImovel))
			return  StatusImovelEnum.getLabel(this.perfilImovel);
		else	
			return perfilImovelFmt;
	}

	public void setPerfilImovelFmt(String perfilImovelFmt) {
		this.perfilImovelFmt = perfilImovelFmt;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
    
}
