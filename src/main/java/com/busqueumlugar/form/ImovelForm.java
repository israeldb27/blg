package com.busqueumlugar.form;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

import com.busqueumlugar.enumerador.StatusImovelEnum;
import com.busqueumlugar.model.Imovelcomentario;
import com.busqueumlugar.model.Imoveldestaque;
import com.busqueumlugar.model.Imovelfavoritos;
import com.busqueumlugar.model.Imovelfotos;
import com.busqueumlugar.model.ImovelPropostas;
import com.busqueumlugar.model.Imovelvisualizado;
import com.busqueumlugar.model.Intermediacao;
import com.busqueumlugar.model.Parceria;
import com.busqueumlugar.model.Usuario;
import com.busqueumlugar.util.AppUtil;
import com.busqueumlugar.util.DateUtil;
import com.busqueumlugar.util.Select;
import com.busqueumlugar.enumerador.AcaoImovelEnum;
import com.paypal.base.codec.binary.Base64;

public class ImovelForm extends BaseForm{
	
	private Long id;
    private String tipoImovel = "";
    private BigDecimal valorImovel;
	private BigDecimal valorCondominio;
	private BigDecimal valorIptu;
    private String descricao = "";
    private String observacao = "";
    private Double area = 0.0d;
    private String endereco = "";
    private String bairro = "";
    private String cidade = "";
    private String estado = "";
    private String uf;
    private Date dataCadastro = new Date();
    private Date dataUltimaAtualizacao = new Date();
    private int idEstado;  
    private int idCidade;
    private int idBairro;
    private Long idUsuario;
    private String acao;
    private int contadorObservacao;
    private String titulo;  
    private Long relevancia;
    private String habilitaBusca;
    private String acessoVisualizacao;
    
    private String valorBusca = "";
    private String opcaoFiltro = "";
    private String opcaoOrdenacao = "";
    private String autorizacaoPropostas = "";
    private byte[] fotoPrincipal;
    private String statusNegociacao;
    
    private String autorizacaoOutroUsuario = "";
    private String perfilImovel;
    private String perfilUsuario = "";
    
    private String descricaoFoto = "";    
   // private String imagemArquivo = "";    
    private String entradaImagemArquivo;
	private String mensagemRetorno = "";    
    private int quantMaxFotos;
    private String codigoIdentificacao;    
    private String valorCodIdentificacao = "";    
    private String opcaoAcaoCompartilhamento = "";        
    private String emailTo;    
    private String isImovelCompartilhado = "";  // Sim ou nao        
    private int valorImovelMin = 0 ; // campos usados para filtrar por valor
    private int valorImovelMax = 0 ;    
    private int etapaCadastroImovel = 0; // 1 - cadastro informacoes basicas, 2 - foto principal, 3 - galeria fotos    
    private String aceitaFinanciamento = "";
    private String autorizaComentario = "";
    private String nomeFinanciadora = "";
    private String opcaoServicosImovel = "";    
    private String interessadoImovel = "N";     
    
    private int quantQuartos = 0;      
    private int quantGaragem = 0;      
    private int quantSuites = 0;        
    private int quantBanheiro = 0; 
    private String qtdQuartos = "0" ;
    private String qtdGaragem = "0" ;
    private String qtdSuites  = "0" ;
    private String qtdBanheiro = "0"; 
    
    private String destaque;    
    private String acaoMenu = "";
    private String usuarioIndicador = "";
    private int quantMaxCompartilhamento = 0;      
    private String idParamServicoSelecionado = "";
    private int totalFotosGaleria = 0;
    private String opcaoAgrupamento = "";
    private String negociacaoImovel = ""; // {'em aberto','fechado'}
    private Double latitude;    
    private Double longitude;
    private String center;
    
    private String habilitaInfoDonoImovel = "";
    private String habilitaFotosImovel = "";
    private String habilitaMapaImovel = "";
    private String acaoBuscaImovelMapa = "";
    private String habilitaBotaoBuscar = "";
    private String aceitaCorretagem = ""; 
    private String descAceitaCorretagemParceria = "";
    private String ativado = "";
    private String aceitaParceria = ""; 
    private String aceitaIntermediacao = "";
    
    private List<Select> listaEstados;
    private List<Select> listaCidades;
    private List<Select> listaBairros;
    
    private MultipartFile fileImovel;
    
    // inicio campos usados para o Detalhes Imovel
    private List<ImovelPropostas> listaPropostas;
    private List<Imovelcomentario> listaComentario;
    private List<Parceria> listaParceria;
    private List<Intermediacao> listaIntermediacao;
	private List<Imovelvisualizado> listaVisita;
	private List<Imovelfavoritos> listaUsuariosInteressados;	
	private List<Usuario> listaUsuariosParceiros;	
	private List<Imovelfotos> listaFotos;
	private List<Imoveldestaque> listaImovelAnuncio;
    private Usuario usuarioDonoImovel;
    private Usuario usuarioCorretorImovel;
    private Usuario usuarioImobiliariaImovel;
	private String novoComentario = "";
	private BigDecimal novaProposta;
	private Intermediacao intermediacaoEnviada = null;
	private String novaSolIntermediacao = "";
	private String novaSolParceria = "";
	private Parceria parceriaEnviada; // este campo  usado para checar se o usuario enviou ou nao uma solicitacao de parceria
	
	private Usuario usuarioIntermediador = null;
	
    // fim campos usados para o Detalhes Imovel
	
	private String enderecoMapa = "";
	private String latitudeFmt = "";
	private String longitudeFmt = "";

	private List<Imovelfotos> listaImovelFotos;	
	private List<Imoveldestaque> listaImoveisDestaque;
	private String dataDestaque = "";	
	
	private String tipoImovelFmt = "";    
    private String acaoFmt = "";
    private String perfilImovelFmt = "";
	private String opcaoDestaque = "";
	private String dataCadastroFmt;
	private Long idUsuarioPerfil;
	
	private String valorMin;
	private String valorMax;
	
	private String valorImovelFmt = "";
	private String valorIptuFmt = "";
	private String valorCondominioFmt = "";
	
	
	public List<Imovelfotos> getListaImovelFotos() {
		return listaImovelFotos;
	}

	public void setListaImovelFotos(List<Imovelfotos> listaImovelFotos) {
		this.listaImovelFotos = listaImovelFotos;
	}

	public Parceria getParceriaEnviada() {
		return parceriaEnviada;
	}

	public void setParceriaEnviada(Parceria parceriaEnviada) {
		this.parceriaEnviada = parceriaEnviada;
	}
	
	public String getLatitudeFmt() {
		return latitudeFmt;
	}

	public void setLatitudeFmt(String latitudeFmt) {
		this.latitudeFmt = latitudeFmt;
	}
	
	public String getLongitudeFmt() {
		return longitudeFmt;
	}

	public void setLongitudeFmt(String longitudeFmt) {
		this.longitudeFmt = longitudeFmt;
	}

	public String getEnderecoMapa() {
		return enderecoMapa;
	}

	public void setEnderecoMapa(String enderecoMapa) {
		this.enderecoMapa = enderecoMapa;
	}

	public List<Imovelfotos> getListaFotos() {
		return listaFotos;
	}

	public void setListaFotos(List<Imovelfotos> listaFotos) {
		this.listaFotos = listaFotos;
	}
		
	   
	public String getNovaSolIntermediacao() {
		return novaSolIntermediacao;
	}

	public void setNovaSolIntermediacao(String novaSolIntermediacao) {
		this.novaSolIntermediacao = novaSolIntermediacao;
	}

	public String getNovaSolParceria() {
		return novaSolParceria;
	}

	public void setNovaSolParceria(String novaSolParceria) {
		this.novaSolParceria = novaSolParceria;
	}

	/**
     * @return the novaProposta
     */
    public BigDecimal getNovaProposta() {
        return novaProposta;
    }

    /**
     * @param novaProposta the novaProposta to set
     */
    public void setNovaProposta(BigDecimal novaProposta) {
        this.novaProposta = novaProposta;
    }
	
	/**
     * @return the novoComentario
     */
    public String getNovoComentario() {
        return novoComentario;
    }

    /**
     * @param novoComentario the novoComentario to set
     */
    public void setNovoComentario(String novoComentario) {
        this.novoComentario = novoComentario;
    }

	public Usuario getUsuarioDonoImovel() {
		return usuarioDonoImovel;
	}

	public void setUsuarioDonoImovel(Usuario usuarioDonoImovel) {
		this.usuarioDonoImovel = usuarioDonoImovel;
	}

	public Usuario getUsuarioCorretorImovel() {
		return usuarioCorretorImovel;
	}

	public void setUsuarioCorretorImovel(Usuario usuarioCorretorImovel) {
		this.usuarioCorretorImovel = usuarioCorretorImovel;
	}

	public Usuario getUsuarioImobiliariaImovel() {
		return usuarioImobiliariaImovel;
	}

	public void setUsuarioImobiliariaImovel(Usuario usuarioImobiliariaImovel) {
		this.usuarioImobiliariaImovel = usuarioImobiliariaImovel;
	}

	private long quantVisualizacoesImovel = 0; // checa a quantidade de visitas que o imovel recebeu - para estatistica
    private long quantUsuariosInteressados = 0;
    
    
    
    public MultipartFile getFileImovel() {
		return fileImovel;
	}

	public void setFileImovel(MultipartFile fileImovel) {
		this.fileImovel = fileImovel;
	}

	public String getEntradaImagemArquivo() {
		return entradaImagemArquivo;
	}

	public void setEntradaImagemArquivo(String entradaImagemArquivo) {
		this.entradaImagemArquivo = entradaImagemArquivo;
	}
	
    /**
     * @return the imagemArquivo
     */
    public String getImagemArquivo() {
    	if ( this != null && this.getFotoPrincipal() != null)
    		return Base64.encodeBase64String(this.getFotoPrincipal());
    	else
    		return "";
    }
    
    
    public List<Select> getListaEstados() {
		return listaEstados;
	}

	public void setListaEstados(List<Select> listaEstados) {
		this.listaEstados = listaEstados;
	}

	public List<Select> getListaCidades() {
		return listaCidades;
	}

	public void setListaCidades(List<Select> listaCidades) {
		this.listaCidades = listaCidades;
	}

	public List<Select> getListaBairros() {
		return listaBairros;
	}

	public void setListaBairros(List<Select> listaBairros) {
		this.listaBairros = listaBairros;
	}

	/**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
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
     * @return the valorImovel
     */
    public BigDecimal getValorImovel() {
        return valorImovel;
    }

    /**
     * @param valorImovel the valorImovel to set
     */
    public void setValorImovel(BigDecimal valorImovel) {
        this.valorImovel = valorImovel;
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
     * @return the observacao
     */
    public String getObservacao() {
        return observacao;
    }

    /**
     * @param observacao the observacao to set
     */
    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    /**
     * @return the area
     */
    public Double getArea() {
        return area;
    }

    /**
     * @param area the area to set
     */
    public void setArea(Double area) {
        this.area = area;
    }

    /**
     * @return the endereco
     */
    public String getEndereco() {
        return endereco;
    }

    /**
     * @param endereco the endereco to set
     */
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    /**
     * @return the bairro
     */
    public String getBairro() {
        return bairro;
    }

    /**
     * @param bairro the bairro to set
     */
    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    /**
     * @return the cidade
     */
    public String getCidade() {
        return cidade;
    }

    /**
     * @param cidade the cidade to set
     */
    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    /**
     * @return the estado
     */
    public String getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * @return the valorBusca
     */
    public String getValorBusca() {
        return valorBusca;
    }

    /**
     * @param valorBusca the valorBusca to set
     */
    public void setValorBusca(String valorBusca) {
        this.valorBusca = valorBusca;
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
     * @return the opcaoFiltro
     */
    public String getOpcaoFiltro() {
        return opcaoFiltro;
    }

    /**
     * @param opcaoFiltro the opcaoFiltro to set
     */
    public void setOpcaoFiltro(String opcaoFiltro) {
        this.opcaoFiltro = opcaoFiltro;
    }

    /**
     * @return the opcaoOrdenacao
     */
    public String getOpcaoOrdenacao() {
        return opcaoOrdenacao;
    }

    /**
     * @param opcaoOrdenacao the opcaoOrdenacao to set
     */
    public void setOpcaoOrdenacao(String opcaoOrdenacao) {
        this.opcaoOrdenacao = opcaoOrdenacao;
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
    
    public String getClassePorAcao(){
        return  AcaoImovelEnum.getClassePorAcao(this.acao);
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
     * @return the idUsuario
     */
    public Long getIdUsuario() {
        return idUsuario;
    }

    /**
     * @param idUsuario the idUsuario to set
     */
    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
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
     * @return the descricaoFoto
     */
    public String getDescricaoFoto() {
        return descricaoFoto;
    }

    /**
     * @param descricaoFoto the descricaoFoto to set
     */
    public void setDescricaoFoto(String descricaoFoto) {
        this.descricaoFoto = descricaoFoto;
    }

    /**
     * @return the mensagemRetorno
     */
    public String getMensagemRetorno() {
        return mensagemRetorno;
    }

    /**
     * @param mensagemRetorno the mensagemRetorno to set
     */
    public void setMensagemRetorno(String mensagemRetorno) {
        this.mensagemRetorno = mensagemRetorno;
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
     * @return the valorCodIdentificacao
     */
    public String getValorCodIdentificacao() {
        return valorCodIdentificacao;
    }

    /**
     * @param valorCodIdentificacao the valorCodIdentificacao to set
     */
    public void setValorCodIdentificacao(String valorCodIdentificacao) {
        this.valorCodIdentificacao = valorCodIdentificacao;
    }

    /**
     * @return the emailTo
     */
    public String getEmailTo() {
        return emailTo;
    }

    /**
     * @param emailTo the emailTo to set
     */
    public void setEmailTo(String emailTo) {
        this.emailTo = emailTo;
    }

    /**
     * @return the opcaoAcaoCompartilhamento
     */
    public String getOpcaoAcaoCompartilhamento() {
        return opcaoAcaoCompartilhamento;
    }

    /**
     * @param opcaoAcaoCompartilhamento the opcaoAcaoCompartilhamento to set
     */
    public void setOpcaoAcaoCompartilhamento(String opcaoAcaoCompartilhamento) {
        this.opcaoAcaoCompartilhamento = opcaoAcaoCompartilhamento;
    }

    /**
     * @return the isImovelCompartilhado
     */
    public String getIsImovelCompartilhado() {
        return isImovelCompartilhado;
    }

    /**
     * @param isImovelCompartilhado the isImovelCompartilhado to set
     */
    public void setIsImovelCompartilhado(String isImovelCompartilhado) {
        this.isImovelCompartilhado = isImovelCompartilhado;
    }

    /**
     * @return the valorImovelMin
     */
    public int getValorImovelMin() {
        return valorImovelMin;
    }

    /**
     * @param valorImovelMin the valorImovelMin to set
     */
    public void setValorImovelMin(int valorImovelMin) {
        this.valorImovelMin = valorImovelMin;
    }

    /**
     * @return the valorImovelMax
     */
    public int getValorImovelMax() {
        return valorImovelMax;
    }

    /**
     * @param valorImovelMax the valorImovelMax to set
     */
    public void setValorImovelMax(int valorImovelMax) {
        this.valorImovelMax = valorImovelMax;
    }

    /**
     * @return the etapaCadastroImovel
     */
    public int getEtapaCadastroImovel() {
        return etapaCadastroImovel;
    }

    /**
     * @param etapaCadastroImovel the etapaCadastroImovel to set
     */
    public void setEtapaCadastroImovel(int etapaCadastroImovel) {
        this.etapaCadastroImovel = etapaCadastroImovel;
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
     * @return the opcaoServicosImovel
     */
    public String getOpcaoServicosImovel() {
        return opcaoServicosImovel;
    }

    /**
     * @param opcaoServicosImovel the opcaoServicosImovel to set
     */
    public void setOpcaoServicosImovel(String opcaoServicosImovel) {
        this.opcaoServicosImovel = opcaoServicosImovel;
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
     * @return the acaoMenu
     */
    public String getAcaoMenu() {
        return acaoMenu;
    }

    /**
     * @param acaoMenu the acaoMenu to set
     */
    public void setAcaoMenu(String acaoMenu) {
        this.acaoMenu = acaoMenu;
    }

    /**
     * @return the usuarioIndicador
     */
    public String getUsuarioIndicador() {
        return usuarioIndicador;
    }

    /**
     * @param usuarioIndicador the usuarioIndicador to set
     */
    public void setUsuarioIndicador(String usuarioIndicador) {
        this.usuarioIndicador = usuarioIndicador;
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
     * @return the quantVisitasImovel
     */
    public long getQuantVisualizacoesImovel() {
        return quantVisualizacoesImovel;
    }

    /**
     * @param quantVisitasImovel the quantVisitasImovel to set
     */
    public void setQuantVisualizacoesImovel(long quantVisualizacoesImovel) {
        this.quantVisualizacoesImovel = quantVisualizacoesImovel;
    }

    /**
     * @return the quantUsuariosInteressados
     */
    public long getQuantUsuariosInteressados() {
        return quantUsuariosInteressados;
    }

    /**
     * @param quantUsuariosInteressados the quantUsuariosInteressados to set
     */
    public void setQuantUsuariosInteressados(long quantUsuariosInteressados) {
        this.quantUsuariosInteressados = quantUsuariosInteressados;
    }

    /**
     * @return the idParamServicoSelecionado
     */
    public String getIdParamServicoSelecionado() {
        return idParamServicoSelecionado;
    }

    /**
     * @param idParamServicoSelecionado the idParamServicoSelecionado to set
     */
    public void setIdParamServicoSelecionado(String idParamServicoSelecionado) {
        this.idParamServicoSelecionado = idParamServicoSelecionado;
    }

    /**
     * @return the totalFotosGaleria
     */
    public int getTotalFotosGaleria() {
        return totalFotosGaleria;
    }

    /**
     * @param totalFotosGaleria the totalFotosGaleria to set
     */
    public void setTotalFotosGaleria(int totalFotosGaleria) {
        this.totalFotosGaleria = totalFotosGaleria;
    }

    /**
     * @return the opcaoAgrupamento
     */
    public String getOpcaoAgrupamento() {
        return opcaoAgrupamento;
    }

    /**
     * @param opcaoAgrupamento the opcaoAgrupamento to set
     */
    public void setOpcaoAgrupamento(String opcaoAgrupamento) {
        this.opcaoAgrupamento = opcaoAgrupamento;
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
     * @return the center
     */
    public String getCenter() {
        return center;
    }

    /**
     * @param center the center to set
     */
    public void setCenter(String center) {
        this.center = center;
    }

   /**
     * @return the simpleModel
     */
 /*   public MapModel getSimpleModel() {
        return simpleModel;
    }

    /**
     * @param simpleModel the simpleModel to set
     */
  /*  public void setSimpleModel(MapModel simpleModel) {
        this.simpleModel = simpleModel;
    }*/

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
     * @return the perfilUsuario
     */
    public String getPerfilUsuario() {
        return perfilUsuario;
    }

    /**
     * @param perfilUsuario the perfilUsuario to set
     */
    public void setPerfilUsuario(String perfilUsuario) {
        this.perfilUsuario = perfilUsuario;
    }

    /**
     * @return the habilitaFotosImovel
     */
    public String getHabilitaFotosImovel() {
        return habilitaFotosImovel;
    }

    /**
     * @param habilitaFotosImovel the habilitaFotosImovel to set
     */
    public void setHabilitaFotosImovel(String habilitaFotosImovel) {
        this.habilitaFotosImovel = habilitaFotosImovel;
    }

    /**
     * @return the habilitaMapaImovel
     */
    public String getHabilitaMapaImovel() {
        return habilitaMapaImovel;
    }

    /**
     * @param habilitaMapaImovel the habilitaMapaImovel to set
     */
    public void setHabilitaMapaImovel(String habilitaMapaImovel) {
        this.habilitaMapaImovel = habilitaMapaImovel;
    }

    /**
     * @return the acaoBuscaImovelMapa
     */
    public String getAcaoBuscaImovelMapa() {
        return acaoBuscaImovelMapa;
    }

    /**
     * @param acaoBuscaImovelMapa the acaoBuscaImovelMapa to set
     */
    public void setAcaoBuscaImovelMapa(String acaoBuscaImovelMapa) {
        this.acaoBuscaImovelMapa = acaoBuscaImovelMapa;
    }

    /**
     * @return the qtdQuartos
     */
    public String getQtdQuartos() {
        return qtdQuartos;
    }

    /**
     * @param qtdQuartos the qtdQuartos to set
     */
    public void setQtdQuartos(String qtdQuartos) {
        this.qtdQuartos = qtdQuartos;
    }

    /**
     * @return the qtdGaragem
     */
    public String getQtdGaragem() {
        return qtdGaragem;
    }

    /**
     * @param qtdGaragem the qtdGaragem to set
     */
    public void setQtdGaragem(String qtdGaragem) {
        this.qtdGaragem = qtdGaragem;
    }

    /**
     * @return the qtdSuites
     */
    public String getQtdSuites() {
        return qtdSuites;
    }

    /**
     * @param qtdSuites the qtdSuites to set
     */
    public void setQtdSuites(String qtdSuites) {
        this.qtdSuites = qtdSuites;
    }

    /**
     * @return the habilitaBotaoBuscar
     */
    public String getHabilitaBotaoBuscar() {
        return habilitaBotaoBuscar;
    }

    /**
     * @param habilitaBotaoBuscar the habilitaBotaoBuscar to set
     */
    public void setHabilitaBotaoBuscar(String habilitaBotaoBuscar) {
        this.habilitaBotaoBuscar = habilitaBotaoBuscar;
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
     * @return the aceitaParceria
     */
    public String getAceitaParceria() {
        return aceitaParceria;
    }

    /**
     * @param aceitaParceria the aceitaParceria to set
     */
    public void setAceitaParceria(String aceitaParceria) {
        this.aceitaParceria = aceitaParceria;
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

	public String getAceitaIntermediacao() {
		return aceitaIntermediacao;
	}

	public void setAceitaIntermediacao(String aceitaIntermediacao) {
		this.aceitaIntermediacao = aceitaIntermediacao;
	}
	
	
    public String getDataDestaque() {
        return dataDestaque;
    }

    public void setDataDestaque(String dataDestaque) {
        this.dataDestaque = dataDestaque;
    }
	
	public List<Imoveldestaque> getListaImoveisDestaque() {
		return listaImoveisDestaque;
	}

	public void setListaImoveisDestaque(List<Imoveldestaque> listaImoveisDestaque) {
		this.listaImoveisDestaque = listaImoveisDestaque;
	}

	public Usuario getUsuarioIntermediador() {
		return usuarioIntermediador;
	}

	public void setUsuarioIntermediador(Usuario usuarioIntermediador) {
		this.usuarioIntermediador = usuarioIntermediador;
	}

	public String getTipoImovelFmt() {
		return tipoImovelFmt;
	}

	public void setTipoImovelFmt(String tipoImovelFmt) {
		this.tipoImovelFmt = tipoImovelFmt;
	}

	public String getAcaoFmt() {
		return acaoFmt;
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
	
	public String getOpcaoDestaque() {
		return opcaoDestaque;
	}

	public void setOpcaoDestaque(String opcaoDestaque) {
		this.opcaoDestaque = opcaoDestaque;
	}

	public String getDataCadastroFmt() {		
		return DateUtil.formataData(this.dataCadastro);		
	}

	public void setDataCadastroFmt(String dataCadastroFmt) {
		this.dataCadastroFmt = dataCadastroFmt;
	}

	public Long getIdUsuarioPerfil() {
		return idUsuarioPerfil;
	}

	public void setIdUsuarioPerfil(Long idUsuarioPerfil) {
		this.idUsuarioPerfil = idUsuarioPerfil;
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

	public String getQtdBanheiro() {
		return qtdBanheiro;
	}

	public void setQtdBanheiro(String qtdBanheiro) {
		this.qtdBanheiro = qtdBanheiro;
	}

	public String getStatusNegociacao() {
		return statusNegociacao;
	}

	public void setStatusNegociacao(String statusNegociacao) {
		this.statusNegociacao = statusNegociacao;
	}

	public List<ImovelPropostas> getListaPropostas() {
		return listaPropostas;
	}

	public void setListaPropostas(List<ImovelPropostas> listaPropostas) {
		this.listaPropostas = listaPropostas;
	}

	public List<Imovelcomentario> getListaComentario() {
		return listaComentario;
	}

	public void setListaComentario(List<Imovelcomentario> listaComentario) {
		this.listaComentario = listaComentario;
	}

	public List<Parceria> getListaParceria() {
		return listaParceria;
	}

	public void setListaParceria(List<Parceria> listaParceria) {
		this.listaParceria = listaParceria;
	}

	public List<Intermediacao> getListaIntermediacao() {
		return listaIntermediacao;
	}

	public void setListaIntermediacao(List<Intermediacao> listaIntermediacao) {
		this.listaIntermediacao = listaIntermediacao;
	}

	public List<Imovelvisualizado> getListaVisita() {
		return listaVisita;
	}

	public void setListaVisita(List<Imovelvisualizado> listaVisita) {
		this.listaVisita = listaVisita;
	}

	public List<Imovelfavoritos> getListaUsuariosInteressados() {
		return listaUsuariosInteressados;
	}

	public void setListaUsuariosInteressados(
			List<Imovelfavoritos> listaUsuariosInteressados) {
		this.listaUsuariosInteressados = listaUsuariosInteressados;
	}

	public List<Usuario> getListaUsuariosParceiros() {
		return listaUsuariosParceiros;
	}

	public void setListaUsuariosParceiros(List<Usuario> listaUsuariosParceiros) {
		this.listaUsuariosParceiros = listaUsuariosParceiros;
	}

	public String getValorMin() {
		return valorMin;
	}

	public void setValorMin(String valorMin) {
		this.valorMin = valorMin;
	}

	public String getValorMax() {
		return valorMax;
	}

	public void setValorMax(String valorMax) {
		this.valorMax = valorMax;
	}

	public List<Imoveldestaque> getListaImovelAnuncio() {
		return listaImovelAnuncio;
	}

	public void setListaImovelAnuncio(List<Imoveldestaque> listaImovelAnuncio) {
		this.listaImovelAnuncio = listaImovelAnuncio;
	}

	public String getValorImovelFmt() {
		return valorImovelFmt;
	}

	public void setValorImovelFmt(String valorImovelFmt) {
		this.valorImovelFmt = valorImovelFmt;
	}

	public String getValorIptuFmt() {
		return valorIptuFmt;
	}

	public void setValorIptuFmt(String valorIptuFmt) {
		this.valorIptuFmt = valorIptuFmt;
	}

	public String getValorCondominioFmt() {
		return valorCondominioFmt;
	}

	public void setValorCondominioFmt(String valorCondominioFmt) {
		this.valorCondominioFmt = valorCondominioFmt;
	}

	public Intermediacao getIntermediacaoEnviada() {
		return intermediacaoEnviada;
	}

	public void setIntermediacaoEnviada(Intermediacao intermediacaoSelecionada) {
		this.intermediacaoEnviada = intermediacaoSelecionada;
	}
	
}
