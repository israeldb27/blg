package com.busqueumlugar.form;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.busqueumlugar.enumerador.RelatorioAdminEnum;
import com.busqueumlugar.model.Imovel;
import com.busqueumlugar.model.Planousuario;
import com.busqueumlugar.model.RelatorioQuantAssinatura;
import com.busqueumlugar.model.RelatorioQuantPlano;
import com.busqueumlugar.model.Servico;
import com.busqueumlugar.model.Usuario;
import com.busqueumlugar.util.Select;


public class AdministracaoForm extends BaseForm { 
	
	private List<Select> listaEstados;
    private List<Select> listaCidades;
    private List<Select> listaBairros;

    private int idEstado = 0;  
    private int idCidade = 0;
    private int idBairro = 0;
    
    private String tipoImovel = "";
    private BigInteger valorImovel = new BigInteger("0") ;
    private String descricao = "";
    private String observacao = "";
    private Double area = 0.0d;
    private String endereco = "";
    private String bairro = "";
    private String cidade = "";
    private String estado = "";
    private Date dataCadastro = new Date();
    private Date dataUltimaAtualizacao = new Date();
    private Long idUsuario;
    private String acao;
    private int contadorObservacao;
    private String statusNegociacao;
    
    private String valorBusca = "";
    private String opcaoFiltro = "";
    private String opcaoOrdenacao = "";
    private String autorizacaoPropostas = "";
    private byte[] fotoPrincipal;

    private String valorCodIdentificacao = "";    
    private String opcaoServicosDisponivelImovel = "";
    private String opcaoServicosDisponivelImovelTempo = "";
    
    private int quantAddServico = 0;
    
    private String perfilImovel = "";
    private String perfilUsuario = "";
    
    private int quantQuartos;      
    private int quantGaragem;      
    private int quantSuites;
    
    private String relatorioSelecionado = "";
    
    private String tipoRelatorio = "";
	private String tipoRelatorioFmt = "";
	private String tipoFiltro;
    
    private int quantTotalUsuarios = 0;
   
    /*
    private Date dataInicio;
    private Date dataFim ;
     */
    
    private String dataInicio;
    private String dataFim ;
    
    private int quantAcessosPeriodo = 0;
    private int quantUsuarioSolServicos = 0;    
    private int quantUsuarioPagoServicos = 0;
    
    private long quantTotal;
    
    private String status = "";

    private List<Planousuario> listaPlanoUsuario = new ArrayList<Planousuario>();
    private List<RelatorioQuantPlano> listaRelatorioPlano = new ArrayList<RelatorioQuantPlano>();
    private List<Servico> listaServicos = new ArrayList<Servico>();
    private List<Usuario> listaUsuarios = new ArrayList<Usuario>();
    private List<RelatorioQuantAssinatura> listaRelatorioQuantAssinatura = new ArrayList<RelatorioQuantAssinatura>();
    private List<Imovel> listaImoveis = new ArrayList<Imovel>();
    
    private String opcaoDestaque = "";
	private String faixaSalarial = "";
	private String sexo = "";
	
	
	
	public List<Imovel> getListaImoveis() {
		return listaImoveis;
	}

	public void setListaImoveis(List<Imovel> listaImoveis) {
		this.listaImoveis = listaImoveis;
	}

	public List<RelatorioQuantAssinatura> getListaRelatorioQuantAssinatura() {
		return listaRelatorioQuantAssinatura;
	}

	public void setListaRelatorioQuantAssinatura(List<RelatorioQuantAssinatura> listaRelatorioQuantAssinatura) {
		this.listaRelatorioQuantAssinatura = listaRelatorioQuantAssinatura;
	}

	public List<Usuario> getListaUsuarios() {
		return listaUsuarios;
	}

	public void setListaUsuarios(List<Usuario> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}

	public List<Servico> getListaServicos() {
		return listaServicos;
	}

	public void setListaServicos(List<Servico> listaServicos) {
		this.listaServicos = listaServicos;
	}

	public List<RelatorioQuantPlano> getListaRelatorioPlano() {
		return listaRelatorioPlano;
	}

	public void setListaRelatorioPlano(List<RelatorioQuantPlano> listaRelatorioPlano) {
		this.listaRelatorioPlano = listaRelatorioPlano;
	}

	public List<Planousuario> getListaPlanoUsuario() {
		return listaPlanoUsuario;
	}

	public void setListaPlanoUsuario(List<Planousuario> listaPlanoUsuario) {
		this.listaPlanoUsuario = listaPlanoUsuario;
	}

	public long getQuantTotal() {
		return quantTotal;
	}

	public void setQuantTotal(long quantTotal) {
		this.quantTotal = quantTotal;
	}


	public int getQuantTotalUsuarios() {
		return quantTotalUsuarios;
	}

	public void setQuantTotalUsuarios(int quantTotalUsuarios) {
		this.quantTotalUsuarios = quantTotalUsuarios;
	}

	public int getQuantAcessosPeriodo() {
		return quantAcessosPeriodo;
	}

	public void setQuantAcessosPeriodo(int quantAcessosPeriodo) {
		this.quantAcessosPeriodo = quantAcessosPeriodo;
	}

	public int getQuantUsuarioSolServicos() {
		return quantUsuarioSolServicos;
	}

	public void setQuantUsuarioSolServicos(int quantUsuarioSolServicos) {
		this.quantUsuarioSolServicos = quantUsuarioSolServicos;
	}

	public int getQuantUsuarioPagoServicos() {
		return quantUsuarioPagoServicos;
	}

	public void setQuantUsuarioPagoServicos(int quantUsuarioPagoServicos) {
		this.quantUsuarioPagoServicos = quantUsuarioPagoServicos;
	}

	public String getTipoRelatorio() {
		return tipoRelatorio;
	}

	public void setTipoRelatorio(String tipoRelatorio) {
		this.tipoRelatorio = tipoRelatorio;
	}
	
	public String getTipoRelatorioFmt() {
		return RelatorioAdminEnum.getLabel(tipoRelatorio);
	}

	public void setTipoRelatorioFmt(String tipoRelatorioFmt) {
		this.tipoRelatorioFmt = tipoRelatorioFmt;
	}

	public String getRelatorioSelecionado() {
		return relatorioSelecionado;
	}

	public void setRelatorioSelecionado(String relatorioSelecionado) {
		this.relatorioSelecionado = relatorioSelecionado;
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
    public BigInteger getValorImovel() {
        return valorImovel;
    }

    /**
     * @param valorImovel the valorImovel to set
     */
    public void setValorImovel(BigInteger valorImovel) {
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
     * @return the opcaoServicosDisponivelImovel
     */
    public String getOpcaoServicosDisponivelImovel() {
        return opcaoServicosDisponivelImovel;
    }

    /**
     * @param opcaoServicosDisponivelImovel the opcaoServicosDisponivelImovel to set
     */
    public void setOpcaoServicosDisponivelImovel(String opcaoServicosDisponivelImovel) {
        this.opcaoServicosDisponivelImovel = opcaoServicosDisponivelImovel;
    }

    /**
     * @return the quantAddServico
     */
    public int getQuantAddServico() {
        return quantAddServico;
    }

    /**
     * @param quantAddServico the quantAddServico to set
     */
    public void setQuantAddServico(int quantAddServico) {
        this.quantAddServico = quantAddServico;
    }

    /**
     * @return the opcaoServicosDisponivelImovelTempo
     */
    public String getOpcaoServicosDisponivelImovelTempo() {
        return opcaoServicosDisponivelImovelTempo;
    }

    /**
     * @param opcaoServicosDisponivelImovelTempo the opcaoServicosDisponivelImovelTempo to set
     */
    public void setOpcaoServicosDisponivelImovelTempo(String opcaoServicosDisponivelImovelTempo) {
        this.opcaoServicosDisponivelImovelTempo = opcaoServicosDisponivelImovelTempo;
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

	public String getPerfilUsuario() {
		return perfilUsuario;
	}

	public void setPerfilUsuario(String perfilUsuario) {
		this.perfilUsuario = perfilUsuario;
	}


	public String getOpcaoDestaque() {
		return opcaoDestaque;
	}

	public void setOpcaoDestaque(String opcaoDestaque) {
		this.opcaoDestaque = opcaoDestaque;
	}

	public String getFaixaSalarial() {
		return faixaSalarial;
	}

	public void setFaixaSalarial(String faixaSalarial) {
		this.faixaSalarial = faixaSalarial;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	

	public String getTipoFiltro() {
		return tipoFiltro;
	}

	public void setTipoFiltro(String tipoFiltro) {
		this.tipoFiltro = tipoFiltro;
	}

	public String getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(String dataInicio) {
		this.dataInicio = dataInicio;
	}

	public String getDataFim() {
		return dataFim;
	}

	public void setDataFim(String dataFim) {
		this.dataFim = dataFim;
	}

	public String getStatusNegociacao() {
		return statusNegociacao;
	}

	public void setStatusNegociacao(String statusNegociacao) {
		this.statusNegociacao = statusNegociacao;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


}
