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
import javax.persistence.Transient;

import com.busqueumlugar.enumerador.StatusPagtoEnum;

@Entity
@Table(name = "servico")
public class Servico implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue    
    @Column(name = "id")
    private Long id;    
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="idImovel")    
    private Imovel imovel;
    
    @Column(name = "formaPgto")
    private String formaPgto;    
        
    @Column(name = "valorServico")
    private double valorServico;
    
    @Column(name = "dataPagto")
    @Temporal(TemporalType.DATE)
    private Date dataPagto;
    
    @Column(name = "statusPgto")
    private String statusPgto;    
    
    @ManyToOne
    @JoinColumn(name="idUsuario")    
    private Usuario usuario;
    
    @Column(name = "dataSolicitacao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataSolicitacao;
    
    @Column(name = "dataAguardandoPagto")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataAguardandoPagto;
    
    @Column(name = "descServico")
    private String descServico;
    
    @Column(name = "operadora")
    private String operadora;
    
    @Column(name = "dataFimServico")
    @Temporal(TemporalType.DATE)
    private Date dataFimServico;
    
    @Column(name = "opcaoTempoPagto")
    private String opcaoTempoPagto; // indica se eh 1 dia, 1 semana, 1 mes ou 1 ano    
    
    @Column(name = "quantFoto")
    private int quantFoto; // este campo indica a quantidade de fotos selecionado eh usado apenas pelo servico Adicionar Fotos    
        
    @Column(name = "tempoDuracao")
    private int tempoDuracao;    
    
    @Column(name = "labelServico")
    private String labelServico;    
        
    @ManyToOne
    @JoinColumn(name="idParamServico")    
    private Paramservico paramServico;
        
    @Column(name = "quantidadeServico")
    private int quantidadeServico;
    
    @Column(name = "tipoServico")
    private String tipoServico;
    
    @Column(name = "associadoPlano")
    private String associadoPlano;
    
    @Column(name = "token")
    private String token;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="idPlanoUsuario")    
    private Planousuario planousuario;
    
    @Transient
    private int quantidade = 0;
    
    @Transient
    private double valorFinanceiro;
    
    @Transient
    private String tipoServicoComum = ""; // {S, N}
	
	@Transient
	private String statusPgtoFmt;
	
	@Transient
	private Usuario usuarioRel;	


    public Servico() {
    }

    public Servico(Long id) {
        this.id = id;
    }

    public Servico(Long id, String formaPgto, double valorServico) {
        this.id = id;        
        this.formaPgto = formaPgto;
        this.valorServico = valorServico;        
    }

    
    
    public Date getDataAguardandoPagto() {
		return dataAguardandoPagto;
	}

	public void setDataAguardandoPagto(Date dataAguardandoPagto) {
		this.dataAguardandoPagto = dataAguardandoPagto;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFormaPgto() {
        return formaPgto;
    }

    public void setFormaPgto(String formaPgto) {
        this.formaPgto = formaPgto;
    }

    public double getValorServico() {
        return valorServico;
    }

    public void setValorServico(double valorServico) {
        this.valorServico = valorServico;
    }

    public Date getDataPagto() {
        return dataPagto;
    }

    public void setDataPagto(Date dataPagto) {
        this.dataPagto = dataPagto;
    }

    public String getStatusPgto() {
        return statusPgto;
    }

    public void setStatusPgto(String statusPgto) {
        this.statusPgto = statusPgto;
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
        if (!(object instanceof Servico)) {
            return false;
        }
        Servico other = (Servico) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.app.servico.Servico[ id=" + id + " ]";
    }

    /**
     * @return the dataSolicitacao
     */
    public Date getDataSolicitacao() {
        return dataSolicitacao;
    }

    /**
     * @param dataSolicitacao the dataSolicitacao to set
     */
    public void setDataSolicitacao(Date dataSolicitacao) {
        this.dataSolicitacao = dataSolicitacao;
    }


    /**
     * @return the operadora
     */
    public String getOperadora() {
        return operadora;
    }

    /**
     * @param operadora the operadora to set
     */
    public void setOperadora(String operadora) {
        this.operadora = operadora;
    }

    /**
     * @return the dataFimServico
     */
    public Date getDataFimServico() {
        return dataFimServico;
    }

    /**
     * @param dataFimServico the dataFimServico to set
     */
    public void setDataFimServico(Date dataFimServico) {
        this.dataFimServico = dataFimServico;
    }

    /**
     * @return the opcaoTempoPagto
     */
    public String getOpcaoTempoPagto() {
        return opcaoTempoPagto;
    }

    /**
     * @param opcaoTempoPagto the opcaoTempoPagto to set
     */
    public void setOpcaoTempoPagto(String opcaoTempoPagto) {
        this.opcaoTempoPagto = opcaoTempoPagto;
    }

    /**
     * @return the descServico
     */
    public String getDescServico() {
        return descServico;
    }

    /**
     * @param descServico the descServico to set
     */
    public void setDescServico(String descServico) {
        this.descServico = descServico;
    }


    /**
     * @return the quantFoto
     */
    public int getQuantFoto() {
        return quantFoto;
    }

    /**
     * @param quantFoto the quantFoto to set
     */
    public void setQuantFoto(int quantFoto) {
        this.quantFoto = quantFoto;
    }

    /**
     * @return the tempoDuracao
     */
    public int getTempoDuracao() {
        return tempoDuracao;
    }

    /**
     * @param tempoDuracao the tempoDuracao to set
     */
    public void setTempoDuracao(int tempoDuracao) {
        this.tempoDuracao = tempoDuracao;
    }

    /**
     * @return the labelServico
     */
    public String getLabelServico() {
        return labelServico;
    }

    /**
     * @param labelServico the labelServico to set
     */
    public void setLabelServico(String labelServico) {
        this.labelServico = labelServico;
    }

    /**
     * @return the quantidade
     */
    public int getQuantidade() {
        return quantidade;
    }

    /**
     * @param quantidade the quantidade to set
     */
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    /**
     * @return the quantidadeServico
     */
    public int getQuantidadeServico() {
        return quantidadeServico;
    }

    /**
     * @param quantidadeServico the quantidadeServico to set
     */
    public void setQuantidadeServico(int quantidadeServico) {
        this.quantidadeServico = quantidadeServico;
    }

    /**
     * @return the tipoServico
     */
    public String getTipoServico() {
        return tipoServico;
    }

    /**
     * @param tipoServico the tipoServico to set
     */
    public void setTipoServico(String tipoServico) {
        this.tipoServico = tipoServico;
    }

    /**
     * @return the associadoPlano
     */
    public String getAssociadoPlano() {
        return associadoPlano;
    }

    /**
     * @param associadoPlano the associadoPlano to set
     */
    public void setAssociadoPlano(String associadoPlano) {
        this.associadoPlano = associadoPlano;
    }

  
    /**
     * @return the valorFinanceiro
     */
    public double getValorFinanceiro() {
        return valorFinanceiro;
    }

    /**
     * @param valorFinanceiro the valorFinanceiro to set
     */
    public void setValorFinanceiro(double valorFinanceiro) {
        this.valorFinanceiro = valorFinanceiro;
    }

    /**
     * @return the tipoServicoComum
     */
    public String getTipoServicoComum() {
        return tipoServicoComum;
    }

    /**
     * @param tipoServicoComum the tipoServicoComum to set
     */
    public void setTipoServicoComum(String tipoServicoComum) {
        this.tipoServicoComum = tipoServicoComum;
    }

	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	public String getStatusPgtoFmt() {
		return StatusPagtoEnum.getLabel(this.statusPgto); 
	}

	public void setStatusPgtoFmt(String statusPgtoFmt) {
		this.statusPgtoFmt = statusPgtoFmt;
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

	public Paramservico getParamServico() {
		return paramServico;
	}

	public void setParamServico(Paramservico paramServico) {
		this.paramServico = paramServico;
	}

	public Planousuario getPlanousuario() {
		return planousuario;
	}

	public void setPlanousuario(Planousuario planousuario) {
		this.planousuario = planousuario;
	}

	public Usuario getUsuarioRel() {
		return usuarioRel;
	}

	public void setUsuarioRel(Usuario usuarioRel) {
		this.usuarioRel = usuarioRel;
	}
}
