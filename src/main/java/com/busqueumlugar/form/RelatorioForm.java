package com.busqueumlugar.form;

import java.util.Date;
import java.util.List;

import com.busqueumlugar.enumerador.RelatorioEnum;
import com.busqueumlugar.model.Formapagamento;
import com.busqueumlugar.model.Infoservico;
import com.busqueumlugar.util.DateUtil;
import com.busqueumlugar.util.Select;
import com.mysql.jdbc.StringUtils;

/**
 *
 * @author isbarr
 */
public class RelatorioForm extends BaseForm{
    
    private Long id;
    private String nome;
    private String descricao;
    private Date dataCriacao;
    private String status;
    private String item;    
    private String tipo;
    
    private String idRelatorioSelecionado;
    private String perfilSelecionado = "";
    
    private String tipoVisualizar = "";    
    private String opcaoOrdenacao = "";
    
    private String statusPlano = "";
    private String buscarUsuario = "";
    private String statusAssinatura = "";
    private int quantMaxRegistrosResultado = 0;
    
    private int idEstado;  
    private int idCidade;
    private int idBairro;
    
    private String acao = "";
    private String tipoImovel = "";
    private String perfilImovel = "";
    private String cobranca = "";
    
    private String opcaoPeriodo = "";    
    private String habilitaCobranca = "";    
    private String opcaoFiltroContato = "";    
    private String tipoAcao = "";
    
    private List<Select> listaEstados;
    private List<Select> listaCidades;
    private List<Select> listaBairros;
    
    private String path = "";
    
    private List<Formapagamento> listaOpcoesFormaPagamento;
    private Long idFormapagamentoSelecionada;
    
    private List<Infoservico> listaInfoServicoPagamento;
    private Long idInfoServicoPagamentoSelecionada;	
    
    private Long idUsuarioSelecionado;
    private UsuarioForm usuarioSessao;
    private long idInfoServico; 
    private String formaPagtoServico;
    private double valorServico;
    private String tipoInfoServico;
    private int tempoDuracao;
    private String tipoServico;
    private int quantidadeServico;
    private String formaPagtoSelecionado;
    private String nomeFormaPagto;
    private String opcao;
    private Long idServicoGerado;
    private String opcaoTempoPagto;
    private String labelServico;   
    private String idSimulaServicoRelatorio = "";
    private String opcaoRelatorioSobreLocalidade = "";
    
    private String dataInicio = "";
    private String dataFim = "";	
	private String itemFmt;  	
	private String faixaSalarial = "";
	private String sexo = "";
	private String perfilUsuario = "";
	private String dataCriacaoFmt; 
    
    

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

	public String getIdSimulaServicoRelatorio() {
		return idSimulaServicoRelatorio;
	}

	public void setIdSimulaServicoRelatorio(String idSimulaServicoRelatorio) {
		this.idSimulaServicoRelatorio = idSimulaServicoRelatorio;
	}

	public Long getIdServicoGerado() {
		return idServicoGerado;
	}

	public void setIdServicoGerado(Long idServicoGerado) {
		this.idServicoGerado = idServicoGerado;
	}

	public String getLabelServico() {
		return labelServico;
	}

	public void setLabelServico(String labelServico) {
		this.labelServico = labelServico;
	}

	public String getOpcaoTempoPagto() {
		return opcaoTempoPagto;
	}

	public void setOpcaoTempoPagto(String opcaoTempoPagto) {
		this.opcaoTempoPagto = opcaoTempoPagto;
	}

	public String getOpcao() {
		return opcao;
	}

	public void setOpcao(String opcao) {
		this.opcao = opcao;
	}

	public double getValorServico() {
		return valorServico;
	}

	public void setValorServico(double valorServico) {
		this.valorServico = valorServico;
	}

	public String getTipoInfoServico() {
		return tipoInfoServico;
	}

	public void setTipoInfoServico(String tipoInfoServico) {
		this.tipoInfoServico = tipoInfoServico;
	}

	public int getTempoDuracao() {
		return tempoDuracao;
	}

	public void setTempoDuracao(int tempoDuracao) {
		this.tempoDuracao = tempoDuracao;
	}

	public String getTipoServico() {
		return tipoServico;
	}

	public void setTipoServico(String tipoServico) {
		this.tipoServico = tipoServico;
	}

	public int getQuantidadeServico() {
		return quantidadeServico;
	}

	public void setQuantidadeServico(int quantidadeServico) {
		this.quantidadeServico = quantidadeServico;
	}

	public String getFormaPagtoSelecionado() {
		return formaPagtoSelecionado;
	}

	public void setFormaPagtoSelecionado(String formaPagtoSelecionado) {
		this.formaPagtoSelecionado = formaPagtoSelecionado;
	}

	public String getNomeFormaPagto() {
		return nomeFormaPagto;
	}

	public void setNomeFormaPagto(String nomeFormaPagto) {
		this.nomeFormaPagto = nomeFormaPagto;
	}

	public String getFormaPagtoServico() {
		return formaPagtoServico;
	}

	public void setFormaPagtoServico(String formaPagtoServico) {
		this.formaPagtoServico = formaPagtoServico;
	}

	public long getIdInfoServico() {
		return idInfoServico;
	}

	public void setIdInfoServico(long idInfoServico) {
		this.idInfoServico = idInfoServico;
	}

	public Long getIdUsuarioSelecionado() {
		return idUsuarioSelecionado;
	}

	public void setIdUsuarioSelecionado(Long idUsuarioSelecionado) {
		this.idUsuarioSelecionado = idUsuarioSelecionado;
	}

	public List<Formapagamento> getListaOpcoesFormaPagamento() {
		return listaOpcoesFormaPagamento;
	}

	public void setListaOpcoesFormaPagamento(
			List<Formapagamento> listaOpcoesFormaPagamento) {
		this.listaOpcoesFormaPagamento = listaOpcoesFormaPagamento;
	}

	public Long getIdFormapagamentoSelecionada() {
		return idFormapagamentoSelecionada;
	}

	public void setIdFormapagamentoSelecionada(Long idFormapagamentoSelecionada) {
		this.idFormapagamentoSelecionada = idFormapagamentoSelecionada;
	}

	public List<Infoservico> getListaInfoServicoPagamento() {
		return listaInfoServicoPagamento;
	}

	public void setListaInfoServicoPagamento(
			List<Infoservico> listaInfoServicoPagamento) {
		this.listaInfoServicoPagamento = listaInfoServicoPagamento;
	}

	public Long getIdInfoServicoPagamentoSelecionada() {
		return idInfoServicoPagamentoSelecionada;
	}

	public void setIdInfoServicoPagamentoSelecionada(
			Long idInfoServicoPagamentoSelecionada) {
		this.idInfoServicoPagamentoSelecionada = idInfoServicoPagamentoSelecionada;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
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

	public String getTipoAcao() {
		return tipoAcao;
	}

	public void setTipoAcao(String tipoAcao) {
		this.tipoAcao = tipoAcao;
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
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
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
     * @return the dataCriacao
     */
    public Date getDataCriacao() {
        return dataCriacao;
    }

    /**
     * @param dataCriacao the dataCriacao to set
     */
    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
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

    /**
     * @return the item
     */
    public String getItem() {
        return item;
    }

    /**
     * @param item the item to set
     */
    public void setItem(String item) {
        this.item = item;
    }

    /**
     * @return the tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * @return the idRelatorioSelecionado
     */
    public String getIdRelatorioSelecionado() {
        return idRelatorioSelecionado;
    }

    /**
     * @param idRelatorioSelecionado the idRelatorioSelecionado to set
     */
    public void setIdRelatorioSelecionado(String idRelatorioSelecionado) {
        this.idRelatorioSelecionado = idRelatorioSelecionado;
    }

	/**
     * @return the perfilSelecionado
     */
    public String getPerfilSelecionado() {
        return perfilSelecionado;
    }

    /**
     * @param perfilSelecionado the perfilSelecionado to set
     */
    public void setPerfilSelecionado(String perfilSelecionado) {
        this.perfilSelecionado = perfilSelecionado;
    }	
	
	/**
     * @return the tipoVisualizar
     */
    public String getTipoVisualizar() {
        return tipoVisualizar;
    }

    /**
     * @param tipoVisualizar the tipoVisualizar to set
     */
    public void setTipoVisualizar(String tipoVisualizar) {
        this.tipoVisualizar = tipoVisualizar;
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
     * @return the cobranca
     */
    public String getCobranca() {
        return cobranca;
    }

    /**
     * @param cobranca the cobranca to set
     */
    public void setCobranca(String cobranca) {
        this.cobranca = cobranca;
    }

    /**
     * @return the opcaoPeriodo
     */
    public String getOpcaoPeriodo() {
        return opcaoPeriodo;
    }

    /**
     * @param opcaoPeriodo the opcaoPeriodo to set
     */
    public void setOpcaoPeriodo(String opcaoPeriodo) {
        this.opcaoPeriodo = opcaoPeriodo;
    }

    /**
     * @return the habilitaCobranca
     */
    public String getHabilitaCobranca() {
        return habilitaCobranca;
    }

    /**
     * @param habilitaCobranca the habilitaCobranca to set
     */
    public void setHabilitaCobranca(String habilitaCobranca) {
        this.habilitaCobranca = habilitaCobranca;
    }
 
    /**
     * @return the opcaoFiltroContato
     */
    public String getOpcaoFiltroContato() {
        return opcaoFiltroContato;
    }

    /**
     * @param opcaoFiltroContato the opcaoFiltroContato to set
     */
    public void setOpcaoFiltroContato(String opcaoFiltroContato) {
        this.opcaoFiltroContato = opcaoFiltroContato;
    }
	
	
	/**
     * @return the itemFmt
     */
    public String getItemFmt() {
    	if (!	StringUtils.isNullOrEmpty(this.item))
    		return  RelatorioEnum.getLabel(this.item);
    	else 
    		return null;         
    }

    /**
     * @param item the itemFmt to set
     */
    public void setItemFmt(String itemFmt) {
        this.itemFmt = itemFmt;
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

	public String getPerfilUsuario() {
		return perfilUsuario;
	}

	public void setPerfilUsuario(String perfilUsuario) {
		this.perfilUsuario = perfilUsuario;
	}

	public String getOpcaoRelatorioSobreLocalidade() {
		return opcaoRelatorioSobreLocalidade;
	}

	public void setOpcaoRelatorioSobreLocalidade(
			String opcaoRelatorioSobreLocalidade) {
		this.opcaoRelatorioSobreLocalidade = opcaoRelatorioSobreLocalidade;
	}

	public String getDataCriacaoFmt() {
		if ( this.dataCriacao != null )
			return DateUtil.formataData(this.dataCriacao);
		else
			return dataCriacaoFmt;
	}

	public void setDataCriacaoFmt(String dataCriacaoFmt) {
		this.dataCriacaoFmt = dataCriacaoFmt;
	}

	public UsuarioForm getUsuarioSessao() {
		return usuarioSessao;
	}

	public void setUsuarioSessao(UsuarioForm usuarioSessao) {
		this.usuarioSessao = usuarioSessao;
	}

	public String getStatusPlano() {
		return statusPlano;
	}

	public void setStatusPlano(String statusPlano) {
		this.statusPlano = statusPlano;
	}

	public String getBuscarUsuario() {
		return buscarUsuario;
	}

	public void setBuscarUsuario(String buscarUsuario) {
		this.buscarUsuario = buscarUsuario;
	}

	public String getStatusAssinatura() {
		return statusAssinatura;
	}

	public void setStatusAssinatura(String statusAssinatura) {
		this.statusAssinatura = statusAssinatura;
	}

	public int getQuantMaxRegistrosResultado() {
		return quantMaxRegistrosResultado;
	}

	public void setQuantMaxRegistrosResultado(int quantMaxRegistrosResultado) {
		this.quantMaxRegistrosResultado = quantMaxRegistrosResultado;
	}

	
}
