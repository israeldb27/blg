package com.busqueumlugar.form;

import java.util.Date;
import java.util.List;

import com.busqueumlugar.enumerador.AcaoServicoEnum;
import com.busqueumlugar.enumerador.StatusPagtoEnum;
import com.busqueumlugar.model.Formapagamento;
import com.busqueumlugar.model.Infoservico;
import com.busqueumlugar.model.Paramservico;
import com.busqueumlugar.model.Plano;
import com.busqueumlugar.model.Planousuario;
import com.busqueumlugar.model.Servico;

/**
 *
 * @author Israel
 */
public class ServicoForm extends BaseForm {
    
    private Long id;
    private long idImovel;    
    private String formaPgto = "";    
    private double valorServico;    
    private Date dataPagto;    
    private String statusPgto;    
    private long idUsuario;
    private String acao; // indica se esta se realizando , por exemplo, um cadastro de imovel
    private Date dataSolicitacao;
    private Date dataAguardandoPagto;
    private String opcaoValorPagto;
    private Date dataInicio = new Date();  
    
    private String ddd;
    private String numeroCelular;
    
    private String servico;    
    private String operadora;    
    private Date dataFimServico;
    private String opcaoTempoPagto;
    private String idSimulaServico = "";
    private String opcaoQuantFoto = ""; // este campo indica a quantidade fotos selecionado e usado apenas no servico Adicionar Fotos
    private int quantFoto;    
    private long idInfoServico;    
    private String formaPagtoSelecionado = "";
    private int tempoDuracao = 0;
    private String nomeFormaPagto = "";    
    private long idParamServico;
    private String labelServico;    
    private String opcaoQuantFotosServImovel = "";    
    private int quantidadeServico;
    private String tipoServico;
    private String opcao;
    private String formaPagtoServico;
    private String associadoPlano;
    private String opcaoFiltro = "";    
    private String opcaoFiltroPlano = "";    
    private Long idServicoGerado;
    private String opcaoOrdenacao = "";
    private String opcaoOrdenacaoPlano = "";
    
    private List<Formapagamento> listaOpcoesFormaPagamento;
    private List<Formapagamento> listaOpcoesFormaPagamentoPlano;
    
	private Long idFormapagamentoSelecionada;
    
    private List<Infoservico> listaInfoServicoPagamento;
    private Long idInfoServicoPagamentoSelecionada;
    private List<Paramservico> listaServicosDisponiveis;
    private Long idServicoSelecionado;
    private Long idPlanoSelecionado;
    private List<Plano> listaPlanosDisponiveis;
    
    private String nomePlanoSelecionado;
    private double valorPlanoSelecionado;
    private Long idSimulaPlanoGerado;
      
    private String labelServicoFmt = "";
    private String labelImovelQuantidade = "";
    
    private long idPlanoUsuario;
	
	private String statusPgtoFmt;
	private String acaoFmt;

    
    public Date getDataAguardandoPagto() {
		return dataAguardandoPagto;
	}

	public void setDataAguardandoPagto(Date dataAguardandoPagto) {
		this.dataAguardandoPagto = dataAguardandoPagto;
	}

	public Long getIdSimulaPlanoGerado() {
		return idSimulaPlanoGerado;
	}

	public void setIdSimulaPlanoGerado(Long idSimulaPlanoGerado) {
		this.idSimulaPlanoGerado = idSimulaPlanoGerado;
	}

	public String getNomePlanoSelecionado() {
		return nomePlanoSelecionado;
	}

	public void setNomePlanoSelecionado(String nomePlanoSelecionado) {
		this.nomePlanoSelecionado = nomePlanoSelecionado;
	}

	public double getValorPlanoSelecionado() {
		return valorPlanoSelecionado;
	}

	public void setValorPlanoSelecionado(double valorPlanoSelecionado) {
		this.valorPlanoSelecionado = valorPlanoSelecionado;
	}

	public Long getIdPlanoSelecionado() {
		return idPlanoSelecionado;
	}

	public void setIdPlanoSelecionado(Long idPlanoSelecionado) {
		this.idPlanoSelecionado = idPlanoSelecionado;
	}

	public List<Formapagamento> getListaOpcoesFormaPagamentoPlano() {
		return listaOpcoesFormaPagamentoPlano;
	}

	public void setListaOpcoesFormaPagamentoPlano(
			List<Formapagamento> listaOpcoesFormaPagamentoPlano) {
		this.listaOpcoesFormaPagamentoPlano = listaOpcoesFormaPagamentoPlano;
	}

	public List<Plano> getListaPlanosDisponiveis() {
		return listaPlanosDisponiveis;
	}

	public void setListaPlanosDisponiveis(List<Plano> listaPlanosDisponiveis) {
		this.listaPlanosDisponiveis = listaPlanosDisponiveis;
	}

	public Long getIdServicoSelecionado() {
		return idServicoSelecionado;
	}

	public void setIdServicoSelecionado(Long idServicoSelecionado) {
		this.idServicoSelecionado = idServicoSelecionado;
	}

	public List<Paramservico> getListaServicosDisponiveis() {
		return listaServicosDisponiveis;
	}

	public void setListaServicosDisponiveis(
			List<Paramservico> listaServicosDisponiveis) {
		this.listaServicosDisponiveis = listaServicosDisponiveis;
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
     * @return the idImovel
     */
    public long getIdImovel() {
        return idImovel;
    }

    /**
     * @param idImovel the idImovel to set
     */
    public void setIdImovel(long idImovel) {
        this.idImovel = idImovel;
    }

    /**
     * @return the formaPgto
     */
    public String getFormaPgto() {
        return formaPgto;
    }

    /**
     * @param formaPgto the formaPgto to set
     */
    public void setFormaPgto(String formaPgto) {
        this.formaPgto = formaPgto;
    }

    /**
     * @return the valorServico
     */
    public double getValorServico() {
        return valorServico;
    }

    /**
     * @param valorServico the valorServico to set
     */
    public void setValorServico(double valorServico) {
        this.valorServico = valorServico;
    }

    /**
     * @return the dataPagto
     */
    public Date getDataPagto() {
        return dataPagto;
    }

    /**
     * @param dataPagto the dataPagto to set
     */
    public void setDataPagto(Date dataPagto) {
        this.dataPagto = dataPagto;
    }

    /**
     * @return the statusPgto
     */
    public String getStatusPgto() {
        return statusPgto;
    }

    /**
     * @param statusPgto the statusPgto to set
     */
    public void setStatusPgto(String statusPgto) {
        this.statusPgto = statusPgto;
    }

    /**
     * @return the idUsuario
     */
    public long getIdUsuario() {
        return idUsuario;
    }

    /**
     * @param idUsuario the idUsuario to set
     */
    public void setIdUsuario(long idUsuario) {
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
     * @return the opcaoValorPagto
     */
    public String getOpcaoValorPagto() {
        return opcaoValorPagto;
    }

    /**
     * @param opcaoValorPagto the opcaoValorPagto to set
     */
    public void setOpcaoValorPagto(String opcaoValorPagto) {
        this.opcaoValorPagto = opcaoValorPagto;
    }

    /**
     * @return the ddd
     */
    public String getDdd() {
        return ddd;
    }

    /**
     * @param ddd the ddd to set
     */
    public void setDdd(String ddd) {
        this.ddd = ddd;
    }

    /**
     * @return the numeroCelular
     */
    public String getNumeroCelular() {
        return numeroCelular;
    }

    /**
     * @param numeroCelular the numeroCelular to set
     */
    public void setNumeroCelular(String numeroCelular) {
        this.numeroCelular = numeroCelular;
    }

    /**
     * @return the servico
     */
    public String getServico() {
        return servico;
    }

    /**
     * @param servico the servico to set
     */
    public void setServico(String servico) {
        this.servico = servico;
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
     * @return the idSimulaServico
     */
    public String getIdSimulaServico() {
        return idSimulaServico;
    }

    /**
     * @param idSimulaServico the idSimulaServico to set
     */
    public void setIdSimulaServico(String idSimulaServico) {
        this.idSimulaServico = idSimulaServico;
    }

    /**
     * @return the opcaoQuantFoto
     */
    public String getOpcaoQuantFoto() {
        return opcaoQuantFoto;
    }

    /**
     * @param opcaoQuantFoto the opcaoQuantFoto to set
     */
    public void setOpcaoQuantFoto(String opcaoQuantFoto) {
        this.opcaoQuantFoto = opcaoQuantFoto;
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
     * @return the idInfoServico
     */
    public long getIdInfoServico() {
        return idInfoServico;
    }

    /**
     * @param idInfoServico the idInfoServico to set
     */
    public void setIdInfoServico(long idInfoServico) {
        this.idInfoServico = idInfoServico;
    }

    /**
     * @return the formaPagtoSelecionado
     */
    public String getFormaPagtoSelecionado() {
        return formaPagtoSelecionado;
    }

    /**
     * @param formaPagtoSelecionado the formaPagtoSelecionado to set
     */
    public void setFormaPagtoSelecionado(String formaPagtoSelecionado) {
        this.formaPagtoSelecionado = formaPagtoSelecionado;
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
     * @return the nomeFormaPagto
     */
    public String getNomeFormaPagto() {
        return nomeFormaPagto;
    }

    /**
     * @param nomeFormaPagto the nomeFormaPagto to set
     */
    public void setNomeFormaPagto(String nomeFormaPagto) {
        this.nomeFormaPagto = nomeFormaPagto;
    }

    /**
     * @return the idParamServico
     */
    public long getIdParamServico() {
        return idParamServico;
    }

    /**
     * @param idParamServico the idParamServico to set
     */
    public void setIdParamServico(long idParamServico) {
        this.idParamServico = idParamServico;
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
     * @return the opcaoQuantFotosServImovel
     */
    public String getOpcaoQuantFotosServImovel() {
        return opcaoQuantFotosServImovel;
    }

    /**
     * @param opcaoQuantFotosServImovel the opcaoQuantFotosServImovel to set
     */
    public void setOpcaoQuantFotosServImovel(String opcaoQuantFotosServImovel) {
        this.opcaoQuantFotosServImovel = opcaoQuantFotosServImovel;
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
     * @return the dataInicio
     */
    public Date getDataInicio() {
        return dataInicio;
    }

    /**
     * @param dataInicio the dataInicio to set
     */
    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    /**
     * @return the opcao
     */
    public String getOpcao() {
        return opcao;
    }

    /**
     * @param opcao the opcao to set
     */
    public void setOpcao(String opcao) {
        this.opcao = opcao;
    }

    /**
     * @return the formaPagtoServico
     */
    public String getFormaPagtoServico() {
        return formaPagtoServico;
    }

    /**
     * @param formaPagtoServico the formaPagtoServico to set
     */
    public void setFormaPagtoServico(String formaPagtoServico) {
        this.formaPagtoServico = formaPagtoServico;
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
     * @return the idServicoGerado
     */
    public Long getIdServicoGerado() {
        return idServicoGerado;
    }

    /**
     * @param idServicoGerado the idServicoGerado to set
     */
    public void setIdServicoGerado(Long idServicoGerado) {
        this.idServicoGerado = idServicoGerado;
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
     * @return the opcaoOrdenacaoPlano
     */
    public String getOpcaoOrdenacaoPlano() {
        return opcaoOrdenacaoPlano;
    }

    /**
     * @param opcaoOrdenacaoPlano the opcaoOrdenacaoPlano to set
     */
    public void setOpcaoOrdenacaoPlano(String opcaoOrdenacaoPlano) {
        this.opcaoOrdenacaoPlano = opcaoOrdenacaoPlano;
    }

    /**
     * @return the opcaoFiltroPlano
     */
    public String getOpcaoFiltroPlano() {
        return opcaoFiltroPlano;
    }

    /**
     * @param opcaoFiltroPlano the opcaoFiltroPlano to set
     */
    public void setOpcaoFiltroPlano(String opcaoFiltroPlano) {
        this.opcaoFiltroPlano = opcaoFiltroPlano;
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

	public String getLabelServicoFmt() {
		return labelServicoFmt;
	}

	public void setLabelServicoFmt(String labelServicoFmt) {
		this.labelServicoFmt = labelServicoFmt;
	}

	public String getLabelImovelQuantidade() {
		return labelImovelQuantidade;
	}

	public void setLabelImovelQuantidade(String labelImovelQuantidade) {
		this.labelImovelQuantidade = labelImovelQuantidade;
	}

	public long getIdPlanoUsuario() {
		return idPlanoUsuario;
	}

	public void setIdPlanoUsuario(long idPlanoUsuario) {
		this.idPlanoUsuario = idPlanoUsuario;
	}
	
	public String getStatusPgtoFmt() {
		return StatusPagtoEnum.getLabel(this.statusPgto); 
	}

	public void setStatusPgtoFmt(String statusPgtoFmt) {
		this.statusPgtoFmt = statusPgtoFmt;
	}
	
	public String getAcaoFmt() {
		return AcaoServicoEnum.getLabel(this.acao); 
	}

	public void setAcaoFmt(String acaoFmt) {
		this.acaoFmt = acaoFmt;
	}
	
	

}
