package com.busqueumlugar.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.busqueumlugar.enumerador.AcaoImovelEnum;
import com.busqueumlugar.enumerador.StatusImovelEnum;
import com.busqueumlugar.enumerador.TipoImovelEnum;
import com.busqueumlugar.util.AppUtil;
import com.busqueumlugar.util.DateUtil;

@Entity
@Table(name = "imovel")
public class Imovel  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8060663211437968571L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;        
    
    @Column(name = "tipoImovel")
    private String tipoImovel;
    
    @Column(name = "valorImovel")
    private BigDecimal valorImovel;
	
	@Column(name = "valorCondominio")
    private BigDecimal valorCondominio;
	
	@Column(name = "valorIptu")
    private BigDecimal valorIptu;
    
    @Column(name = "descricao")
    private String descricao;
    
    @Column(name = "observacao")
    private String observacao;
    
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "area")
    private Double area;
    
    @Column(name = "endereco")
    private String endereco;
    
    @Column(name = "bairro")
    private String bairro;
    
    @Column(name = "cidade")
    private String cidade;
    
    @Column(name = "estado")
    private String estado; 
    
    @Column(name = "uf")
    private String uf;
     
    @ManyToOne
    @JoinColumn(name="idUsuario")    
    private Usuario usuario;    
    
    @Column(name = "dataCadastro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCadastro;    
        
    @Column(name = "dataUltimaAtualizacao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataUltimaAtualizacao; 
    
    @Column(name = "idEstado")
    private int idEstado;    
    
    @Column(name = "idCidade")
    private int idCidade;    
    
    @Column(name = "idBairro")
    private int idBairro;
    
    @Column(name = "acao")
    private String acao;  // indica se  aluguel ou compra      
    
    @Column(name = "contadorObservacao")
    private int contadorObservacao;
    
    @Column(name = "autorizacaoPropostas")
    private String autorizacaoPropostas;    
    
    @Lob
    @Column(name = "fotoPrincipal")
    private byte[] fotoPrincipal;
    
    @Column(name = "autorizacaoOutroUsuario")
    private String autorizacaoOutroUsuario; // autoriza outro usuario corretor ou imobiliaria para trabalhar neste imovel
    
    @Column(name = "perfilImovel")
    private String perfilImovel;
    
    @Column(name = "titulo")
    private String titulo;    
    
    @Column(name = "quantMaxFotos")
    private int quantMaxFotos;        
    
    @Column(name = "codigoIdentificacao")
    private String codigoIdentificacao;
    
    @Column(name = "aceitaFinanciamento")
    private String aceitaFinanciamento;
    
    @Column(name = "autorizaComentario")
    private String autorizaComentario;
    
    @Column(name = "nomeFinanciadora")
    private String nomeFinanciadora;    
    
    @Column(name = "quantMaxCompartilhamento")
    private int quantMaxCompartilhamento;    
    
    @Column(name = "quantQuartos")
    private int quantQuartos;      
    
    @Column(name = "quantGaragem")
    private int quantGaragem;      
    
	@Column(name = "quantSuites")
    private int quantSuites;
	
	@Column(name = "quantBanheiro")
    private int quantBanheiro;  
    
    @Column(name = "destaque")
    private String destaque;
    
    @Column(name = "negociacaoImovel")
    private String negociacaoImovel; // {'em aberto','fechado'}
    
    @Column(name = "latitude")
    private Double latitude;
    
    @Column(name = "longitude")
    private Double longitude;
    
    @Column(name = "habilitaInfoDonoImovel")
    private String habilitaInfoDonoImovel;
    
    @Column(name = "aceitaCorretagem")
    private String aceitaCorretagem;    
    
    @Column(name = "descAceitaCorretagemParceria")
    private String descAceitaCorretagemParceria;    
            
    @Column(name = "ativado")
    private String ativado;
    
    @Column(name = "relevancia")
    private Long relevancia;    
        
    @Column(name = "habilitaBusca")
    private String habilitaBusca;    
        
    @Column(name = "acessoVisualizacao")
    private String acessoVisualizacao;  // {T - todos, N - ninguem, C - apenas os meus contatos}
  
    @Transient
    private String imagemArquivo = "";   
    
    @Transient
    private Date dataInteresse;
    
    @Transient
    private int quantidade = 0;
  
	@Transient
    private String interessadoImovel = ""; // este campo � usado para indicar se o usu�rio est� interessado ou nao no imovel
        
	@Transient
    private long quantPropostas;
    
    @Transient
    private long quantNovasPropostas;
    
    @Transient
    private long quantComentarios;
    
    @Transient
    private long quantNovosComentarios;
    
    @Transient
    private long quantImoveisFavoritos;
    
    @Transient
    private long quantNovosImoveisFavoritos;
    
    @Transient
    private long quantImoveisIndicados;
    
    @Transient
    private long quantNovosImoveisIndicados;
    
    @Transient
    private long quantImoveisVisitados;
    
    @Transient
    private long quantNovosImoveisVisitados;
    
    @Transient
    private long quantImovelParceria;
    
    @Transient
    private long quantNovosImovelParceria;
    
    @Transient
    private long quantImovelIntermediacao;
    
    @Transient
    private long quantNovosImovelIntermediacao;
    
    @Transient
    private double valorMetroQuadrado; // este campo eh usado no comparativo imoveis. um campo derivado ( valor imovel / area m2)
    
    @Transient
    private String tipoImovelFmt = "";
    
    @Transient
    private String acaoFmt = "";
    
    @Transient
    private String perfilImovelFmt = ""; 
	
	@Transient
	private String latitudeFmt = "";
	
	@Transient
	private String longitudeFmt = "";
	
	@Transient
	private String dataCadastroFmt = "";
	
	@Transient
	private String perfilUsuario = "";
    
    
    public double getValorMetroQuadrado() {
		return (this.valorImovel.doubleValue() / this.area );
	}

	public void setValorMetroQuadrado(double valorMetroQuadrado) {
		this.valorMetroQuadrado = valorMetroQuadrado;
	}

	


	public Imovel() {
    }

    public Imovel(Long id) {
        this.id = id;
    }

    public Imovel(Long id, String tipoImovel) {
        this.id = id;
        this.tipoImovel = tipoImovel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoImovel() {
        return tipoImovel;
    }

    public void setTipoImovel(String tipoImovel) {
        this.tipoImovel = tipoImovel;
    }

    public BigDecimal  getValorImovel() {
        return valorImovel;
    }

    public void setValorImovel(BigDecimal  valorImovel) {
        this.valorImovel = valorImovel;
    }
	
	public BigDecimal  getValorCondominio() {
        return valorCondominio;
    }

    public void setValorCondominio(BigDecimal  valorCondominio) {
        this.valorCondominio = valorCondominio;
    }
	
	public BigDecimal getValorIptu() {
        return valorIptu;
    }

    public void setValorIptu(BigDecimal  valorIptu) {
        this.valorIptu = valorIptu;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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
        if (!(object instanceof Imovel)) {
            return false;
        }
        Imovel other = (Imovel) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.app.imovel.Imovel[ id=" + id + " ]";
    }

   
    /**
     * @return the dataCadastro
     */
    public Date getDataCadastro() {
        return dataCadastro;
    }

    /**
     * @param dataCadastro the dataCadastro to set
     */
    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    /**
     * @return the dataUltimaAtualizacao
     */
    public Date getDataUltimaAtualizacao() {
        return dataUltimaAtualizacao;
    }

    /**
     * @param dataUltimaAtualizacao the dataUltimaAtualizacao to set
     */
    public void setDataUltimaAtualizacao(Date dataUltimaAtualizacao) {
        this.dataUltimaAtualizacao = dataUltimaAtualizacao;
    }
   

    /**
     * @return the idEstado
     */
    public int getIdEstado() {
        return idEstado;
    }

    /**
     * @param idEstado the idEstado to set
     */
    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
    }

    /**
     * @return the idCidade
     */
    public int getIdCidade() {
        return idCidade;
    }

    /**
     * @param idCidade the idCidade to set
     */
    public void setIdCidade(int idCidade) {
        this.idCidade = idCidade;
    }

    /**
     * @return the idBairro
     */
    public int getIdBairro() {
        return idBairro;
    }

    /**
     * @param idBairro the idBairro to set
     */
    public void setIdBairro(int idBairro) {
        this.idBairro = idBairro;
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

    /**
     * @return the contadorObservacao
     */
    public int getContadorObservacao() {
        return contadorObservacao;
    }

    /**
     * @param contadorObservacao the contadorObservacao to set
     */
    public void setContadorObservacao(int contadorObservacao) {
        this.contadorObservacao = contadorObservacao;
    }

    /**
     * @return the autorizacaoPropostas
     */
    public String getAutorizacaoPropostas() {
        return autorizacaoPropostas;
    }

    /**
     * @param autorizacaoPropostas the autorizacaoPropostas to set
     */
    public void setAutorizacaoPropostas(String autorizacaoPropostas) {
        this.autorizacaoPropostas = autorizacaoPropostas;
    }

    /**
     * @return the fotoPrincipal
     */
    public byte[] getFotoPrincipal() {
        return fotoPrincipal;
    }

    /**
     * @param fotoPrincipal the fotoPrincipal to set
     */
    public void setFotoPrincipal(byte[] fotoPrincipal) {
        this.fotoPrincipal = fotoPrincipal;
    }


    /**
     * @return the imagemArquivo
     */
    public String getImagemArquivo() {
    	if ( this != null && this.id != null)
    		return AppUtil.carregaFotoPrincipalImovel(this);
    	else
    		return imagemArquivo;
    }

    /**
     * @param imagemArquivo the imagemArquivo to set
     */
    public void setImagemArquivo(String imagemArquivo) {
    	this.imagemArquivo = imagemArquivo;
    }

    /**
     * @return the autorizacaoOutroUsuario
     */
    public String getAutorizacaoOutroUsuario() {
        return autorizacaoOutroUsuario;
    }

    /**
     * @param autorizacaoOutroUsuario the autorizacaoOutroUsuario to set
     */
    public void setAutorizacaoOutroUsuario(String autorizacaoOutroUsuario) {
        this.autorizacaoOutroUsuario = autorizacaoOutroUsuario;
    }

    /**
     * @return the perfilImovel
     */
    public String getPerfilImovel() {
        return perfilImovel;
    }

    /**
     * @param perfilImovel the perfilImovel to set
     */
    public void setPerfilImovel(String perfilImovel) {
        this.perfilImovel = perfilImovel;
    }

  
    /**
     * @return the titulo
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * @param titulo the titulo to set
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * @return the quantMaxFotos
     */
    public int getQuantMaxFotos() {
        return quantMaxFotos;
    }

    /**
     * @param quantMaxFotos the quantMaxFotos to set
     */
    public void setQuantMaxFotos(int quantMaxFotos) {
        this.quantMaxFotos = quantMaxFotos;
    }

    /**
     * @return the codigoIdentificacao
     */
    public String getCodigoIdentificacao() {
        return codigoIdentificacao;
    }

    /**
     * @param codigoIdentificacao the codigoIdentificacao to set
     */
    public void setCodigoIdentificacao(String codigoIdentificacao) {
        this.codigoIdentificacao = codigoIdentificacao;
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
     * @return the aceitaFinanciamento
     */
    public String getAceitaFinanciamento() {
        return aceitaFinanciamento;
    }

    /**
     * @param aceitaFinanciamento the aceitaFinanciamento to set
     */
    public void setAceitaFinanciamento(String aceitaFinanciamento) {
        this.aceitaFinanciamento = aceitaFinanciamento;
    }

    /**
     * @return the autorizaComentario
     */
    public String getAutorizaComentario() {
        return autorizaComentario;
    }

    /**
     * @param autorizaComentario the autorizaComentario to set
     */
    public void setAutorizaComentario(String autorizaComentario) {
        this.autorizaComentario = autorizaComentario;
    }

    /**
     * @return the nomeFinanciadora
     */
    public String getNomeFinanciadora() {
        return nomeFinanciadora;
    }

    /**
     * @param nomeFinanciadora the nomeFinanciadora to set
     */
    public void setNomeFinanciadora(String nomeFinanciadora) {
        this.nomeFinanciadora = nomeFinanciadora;
    }

    /**
     * @return the quantMaxCompartilhamento
     */
    public int getQuantMaxCompartilhamento() {
        return quantMaxCompartilhamento;
    }

    /**
     * @param quantMaxCompartilhamento the quantMaxCompartilhamento to set
     */
    public void setQuantMaxCompartilhamento(int quantMaxCompartilhamento) {
        this.quantMaxCompartilhamento = quantMaxCompartilhamento;
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

    /**
     * @return the destaque
     */
    public String getDestaque() {
        return destaque;
    }

    /**
     * @param destaque the destaque to set
     */
    public void setDestaque(String destaque) {
        this.destaque = destaque;
    }

    /**
     * @return the interessadoImovel
     */
    public String getInteressadoImovel() {
        return interessadoImovel;
    }

    /**
     * @param interessadoImovel the interessadoImovel to set
     */
    public void setInteressadoImovel(String interessadoImovel) {
        this.interessadoImovel = interessadoImovel;
    }
	
    /**
     * @return the negociacaoImovel
     */
    public String getNegociacaoImovel() {
        return negociacaoImovel;
    }

    /**
     * @param negociacaoImovel the negociacaoImovel to set
     */
    public void setNegociacaoImovel(String negociacaoImovel) {
        this.negociacaoImovel = negociacaoImovel;
    }

    /**
     * @return the latitude
     */
    public Double getLatitude() {
        return latitude;
    }

    /**
     * @param latitude the latitude to set
     */
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    /**
     * @return the longitude
     */
    public Double getLongitude() {
        return longitude;
    }

    /**
     * @param longitude the longitude to set
     */
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    /**
     * @return the habilitaInfoDonoImovel
     */
    public String getHabilitaInfoDonoImovel() {
        return habilitaInfoDonoImovel;
    }

    /**
     * @param habilitaInfoDonoImovel the habilitaInfoDonoImovel to set
     */
    public void setHabilitaInfoDonoImovel(String habilitaInfoDonoImovel) {
        this.habilitaInfoDonoImovel = habilitaInfoDonoImovel;
    }

    /**
     * @return the aceitaCorretagem
     */
    public String getAceitaCorretagem() {
        return aceitaCorretagem;
    }

    /**
     * @param aceitaCorretagem the aceitaCorretagem to set
     */
    public void setAceitaCorretagem(String aceitaCorretagem) {
        this.aceitaCorretagem = aceitaCorretagem;
    }    

    /**
     * @return the descAceitaCorretagemParceria
     */
    public String getDescAceitaCorretagemParceria() {
        return descAceitaCorretagemParceria;
    }

    /**
     * @param descAceitaCorretagemParceria the descAceitaCorretagemParceria to set
     */
    public void setDescAceitaCorretagemParceria(String descAceitaCorretagemParceria) {
        this.descAceitaCorretagemParceria = descAceitaCorretagemParceria;
    }

    /**
     * @return the ativado
     */
    public String getAtivado() {
        return ativado;
    }

    /**
     * @param ativado the ativado to set
     */
    public void setAtivado(String ativado) {
        this.ativado = ativado;
    }

    /**
     * @return the relevancia
     */
    public Long getRelevancia() {
        return relevancia;
    }

    /**
     * @param relevancia the relevancia to set
     */
    public void setRelevancia(Long relevancia) {
        this.relevancia = relevancia;
    }

    /**
     * @return the habilitaBusca
     */
    public String getHabilitaBusca() {
        return habilitaBusca;
    }

    /**
     * @param habilitaBusca the habilitaBusca to set
     */
    public void setHabilitaBusca(String habilitaBusca) {
        this.habilitaBusca = habilitaBusca;
    }

    /**
     * @return the acessoVisualizacao
     */
    public String getAcessoVisualizacao() {
        return acessoVisualizacao;
    }

    /**
     * @param acessoVisualizacao the acessoVisualizacao to set
     */
    public void setAcessoVisualizacao(String acessoVisualizacao) {
        this.acessoVisualizacao = acessoVisualizacao;
    }
  	
	public void setQuantNovasPropostas(int quantNovasPropostas) {
		this.quantNovasPropostas = quantNovasPropostas;
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
	
    public String getClassePorAcao(){
        return  AcaoImovelEnum.getClassePorAcao(this.acao);
    }

	public void setAcaoFmt(String acaoFmt) {
		this.acaoFmt = acaoFmt;
	}

	public String getPerfilImovelFmt() {
		return  StatusImovelEnum.getLabel(this.perfilImovel);
	}

	public void setPerfilImovelFmt(String perfilImovelFmt) {
		this.perfilImovelFmt = perfilImovelFmt;
	}
	
	public String getLatitudeFmt() {
		return String.valueOf(this.latitude);
	}

	public void setLatitudeFmt(String latitudeFmt) {
		this.latitudeFmt = latitudeFmt;
	}
	
	public String getLongitudeFmt() {
		return String.valueOf(this.longitude);
	}

	public void setLongitudeFmt(String longitudeFmt) {
		this.longitudeFmt = longitudeFmt;
	}
	
	public String getDataCadastroFmt() {		
		return DateUtil.formataData(this.dataCadastro);		
	}

	public void setDataCadastroFmt(String dataCadastroFmt) {
		this.dataCadastroFmt = dataCadastroFmt;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
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

	public Date getDataInteresse() {
		return dataInteresse;
	}

	public void setDataInteresse(Date dataInteresse) {
		this.dataInteresse = dataInteresse;
	}

	public String getPerfilUsuario() {
		if ( this.usuario != null)
			return usuario.getPerfil();
		else
			return perfilUsuario;
	}

	public void setPerfilUsuario(String perfilUsuario) {
		this.perfilUsuario = perfilUsuario;
	}

	public long getQuantPropostas() {
		return quantPropostas;
	}

	public void setQuantPropostas(long quantPropostas) {
		this.quantPropostas = quantPropostas;
	}

	public long getQuantNovasPropostas() {
		return quantNovasPropostas;
	}

	public void setQuantNovasPropostas(long quantNovasPropostas) {
		this.quantNovasPropostas = quantNovasPropostas;
	}

	public long getQuantComentarios() {
		return quantComentarios;
	}

	public void setQuantComentarios(long quantComentarios) {
		this.quantComentarios = quantComentarios;
	}

	public long getQuantNovosComentarios() {
		return quantNovosComentarios;
	}

	public void setQuantNovosComentarios(long quantNovosComentarios) {
		this.quantNovosComentarios = quantNovosComentarios;
	}

	public long getQuantImoveisFavoritos() {
		return quantImoveisFavoritos;
	}

	public void setQuantImoveisFavoritos(long quantImoveisFavoritos) {
		this.quantImoveisFavoritos = quantImoveisFavoritos;
	}

	public long getQuantNovosImoveisFavoritos() {
		return quantNovosImoveisFavoritos;
	}

	public void setQuantNovosImoveisFavoritos(long quantNovosImoveisFavoritos) {
		this.quantNovosImoveisFavoritos = quantNovosImoveisFavoritos;
	}

	public long getQuantImoveisIndicados() {
		return quantImoveisIndicados;
	}

	public void setQuantImoveisIndicados(long quantImoveisIndicados) {
		this.quantImoveisIndicados = quantImoveisIndicados;
	}

	public long getQuantNovosImoveisIndicados() {
		return quantNovosImoveisIndicados;
	}

	public void setQuantNovosImoveisIndicados(long quantNovosImoveisIndicados) {
		this.quantNovosImoveisIndicados = quantNovosImoveisIndicados;
	}

	public long getQuantImoveisVisitados() {
		return quantImoveisVisitados;
	}

	public void setQuantImoveisVisitados(long quantImoveisVisitados) {
		this.quantImoveisVisitados = quantImoveisVisitados;
	}

	public long getQuantNovosImoveisVisitados() {
		return quantNovosImoveisVisitados;
	}

	public void setQuantNovosImoveisVisitados(long quantNovosImoveisVisitados) {
		this.quantNovosImoveisVisitados = quantNovosImoveisVisitados;
	}

	public long getQuantImovelParceria() {
		return quantImovelParceria;
	}

	public void setQuantImovelParceria(long quantImovelParceria) {
		this.quantImovelParceria = quantImovelParceria;
	}

	public long getQuantNovosImovelParceria() {
		return quantNovosImovelParceria;
	}

	public void setQuantNovosImovelParceria(long quantNovosImovelParceria) {
		this.quantNovosImovelParceria = quantNovosImovelParceria;
	}

	public long getQuantImovelIntermediacao() {
		return quantImovelIntermediacao;
	}

	public void setQuantImovelIntermediacao(long quantImovelIntermediacao) {
		this.quantImovelIntermediacao = quantImovelIntermediacao;
	}

	public long getQuantNovosImovelIntermediacao() {
		return quantNovosImovelIntermediacao;
	}

	public void setQuantNovosImovelIntermediacao(long quantNovosImovelIntermediacao) {
		this.quantNovosImovelIntermediacao = quantNovosImovelIntermediacao;
	}

}
