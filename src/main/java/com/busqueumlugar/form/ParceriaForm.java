package com.busqueumlugar.form;

import java.util.Date;
import java.util.List;

import com.busqueumlugar.util.Select;

public class ParceriaForm extends BaseForm{
	
	private Long id;
	private long idImovel;    
    private long idDonoImovel;    
    private long idUsuarioSolicitante;    
    private Date dataSolicitacao;    
    private Date dataResposta;
    private String status;    
    
    private String descricao;
    private String statusLeitura;    
    private String descricaoCompartilhamento;
    private String tipoImovelCompartilhado; // {I - intermediacao ; P - parceria }
    
    private int idEstado;
    private int idCidade;
    private int idBairro;
    private List<Select> listaEstados;
    private List<Select> listaCidades;
    private List<Select> listaBairros;
    private String tipoImovel = "";
    private String acao = "";
    private String perfil = "";
    private String tipoFiltro = "";
    
    private String opcaoOrdenacao = "";
    private String tipoLista = "";
    private String opcaoVisualizacao = "";
	 
    private int idEstadoAgruparImoveis;
    private int idCidadeAgruparImoveis;
    private int idBairroAgruparImoveis;
    
    private String acaoAgruparImoveis;
    private String tipoImovelAgruparImoveis;
    
    private String opcaoContatoAgruparUsuarios;
    private boolean temFiltroContatoAgruparUsuarios;
    
    private String opcaoPerfilContatoAgruparUsuarios;
    private boolean temFiltroPerfilContatoAgruparUsuarios;

    private String perfilImovelFmt = "";
    private String tipoImovelFmt = "";
    
    private String valorMin;
   	private String valorMax;
   	private int quantQuartos = 0;      
    private int quantGaragem = 0;      
    private int quantSuites = 0;        
    private int quantBanheiro = 0;
    private String perfilImovel;

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public long getIdImovel() {
		return idImovel;
	}

	public void setIdImovel(long idImovel) {
		this.idImovel = idImovel;
	}

	public long getIdDonoImovel() {
		return idDonoImovel;
	}

	public void setIdDonoImovel(long idDonoImovel) {
		this.idDonoImovel = idDonoImovel;
	}

	public long getIdUsuarioSolicitante() {
		return idUsuarioSolicitante;
	}

	public void setIdUsuarioSolicitante(long idUsuarioSolicitante) {
		this.idUsuarioSolicitante = idUsuarioSolicitante;
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

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getStatusLeitura() {
		return statusLeitura;
	}

	public void setStatusLeitura(String statusLeitura) {
		this.statusLeitura = statusLeitura;
	}

	public String getDescricaoCompartilhamento() {
		return descricaoCompartilhamento;
	}

	public void setDescricaoCompartilhamento(String descricaoCompartilhamento) {
		this.descricaoCompartilhamento = descricaoCompartilhamento;
	}

	public String getTipoImovelCompartilhado() {
		return tipoImovelCompartilhado;
	}

	public void setTipoImovelCompartilhado(String tipoImovelCompartilhado) {
		this.tipoImovelCompartilhado = tipoImovelCompartilhado;
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

	public String getTipoImovel() {
		return tipoImovel;
	}

	public void setTipoImovel(String tipoImovel) {
		this.tipoImovel = tipoImovel;
	}

	public String getAcao() {
		return acao;
	}

	public void setAcao(String acao) {
		this.acao = acao;
	}

	public String getPerfil() {
		return perfil;
	}

	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}

	public String getTipoFiltro() {
		return tipoFiltro;
	}

	public void setTipoFiltro(String tipoFiltro) {
		this.tipoFiltro = tipoFiltro;
	}

	public String getOpcaoOrdenacao() {
		return opcaoOrdenacao;
	}

	public void setOpcaoOrdenacao(String opcaoOrdenacao) {
		this.opcaoOrdenacao = opcaoOrdenacao;
	}

	public String getTipoLista() {
		return tipoLista;
	}

	public void setTipoLista(String tipoLista) {
		this.tipoLista = tipoLista;
	}

	public String getOpcaoVisualizacao() {
		return opcaoVisualizacao;
	}

	public void setOpcaoVisualizacao(String opcaoVisualizacao) {
		this.opcaoVisualizacao = opcaoVisualizacao;
	}

	public int getIdEstadoAgruparImoveis() {
		return idEstadoAgruparImoveis;
	}

	public void setIdEstadoAgruparImoveis(int idEstadoAgruparImoveis) {
		this.idEstadoAgruparImoveis = idEstadoAgruparImoveis;
	}

	public int getIdCidadeAgruparImoveis() {
		return idCidadeAgruparImoveis;
	}

	public void setIdCidadeAgruparImoveis(int idCidadeAgruparImoveis) {
		this.idCidadeAgruparImoveis = idCidadeAgruparImoveis;
	}

	public int getIdBairroAgruparImoveis() {
		return idBairroAgruparImoveis;
	}

	public void setIdBairroAgruparImoveis(int idBairroAgruparImoveis) {
		this.idBairroAgruparImoveis = idBairroAgruparImoveis;
	}

	public String getAcaoAgruparImoveis() {
		return acaoAgruparImoveis;
	}

	public void setAcaoAgruparImoveis(String acaoAgruparImoveis) {
		this.acaoAgruparImoveis = acaoAgruparImoveis;
	}

	public String getTipoImovelAgruparImoveis() {
		return tipoImovelAgruparImoveis;
	}

	public void setTipoImovelAgruparImoveis(String tipoImovelAgruparImoveis) {
		this.tipoImovelAgruparImoveis = tipoImovelAgruparImoveis;
	}

	public String getOpcaoContatoAgruparUsuarios() {
		return opcaoContatoAgruparUsuarios;
	}

	public void setOpcaoContatoAgruparUsuarios(String opcaoContatoAgruparUsuarios) {
		this.opcaoContatoAgruparUsuarios = opcaoContatoAgruparUsuarios;
	}

	public boolean isTemFiltroContatoAgruparUsuarios() {
		return temFiltroContatoAgruparUsuarios;
	}

	public void setTemFiltroContatoAgruparUsuarios(
			boolean temFiltroContatoAgruparUsuarios) {
		this.temFiltroContatoAgruparUsuarios = temFiltroContatoAgruparUsuarios;
	}

	public String getOpcaoPerfilContatoAgruparUsuarios() {
		return opcaoPerfilContatoAgruparUsuarios;
	}

	public void setOpcaoPerfilContatoAgruparUsuarios(
			String opcaoPerfilContatoAgruparUsuarios) {
		this.opcaoPerfilContatoAgruparUsuarios = opcaoPerfilContatoAgruparUsuarios;
	}

	public boolean isTemFiltroPerfilContatoAgruparUsuarios() {
		return temFiltroPerfilContatoAgruparUsuarios;
	}

	public void setTemFiltroPerfilContatoAgruparUsuarios(
			boolean temFiltroPerfilContatoAgruparUsuarios) {
		this.temFiltroPerfilContatoAgruparUsuarios = temFiltroPerfilContatoAgruparUsuarios;
	}

	public String getPerfilImovelFmt() {
		return perfilImovelFmt;
	}

	public void setPerfilImovelFmt(String perfilImovelFmt) {
		this.perfilImovelFmt = perfilImovelFmt;
	}

	public String getTipoImovelFmt() {
		return tipoImovelFmt;
	}

	public void setTipoImovelFmt(String tipoImovelFmt) {
		this.tipoImovelFmt = tipoImovelFmt;
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

	public int getQuantQuartos() {
		return quantQuartos;
	}

	public void setQuantQuartos(int quantQuartos) {
		this.quantQuartos = quantQuartos;
	}

	public int getQuantGaragem() {
		return quantGaragem;
	}

	public void setQuantGaragem(int quantGaragem) {
		this.quantGaragem = quantGaragem;
	}

	public int getQuantSuites() {
		return quantSuites;
	}

	public void setQuantSuites(int quantSuites) {
		this.quantSuites = quantSuites;
	}

	public int getQuantBanheiro() {
		return quantBanheiro;
	}

	public void setQuantBanheiro(int quantBanheiro) {
		this.quantBanheiro = quantBanheiro;
	}

	public String getPerfilImovel() {
		return perfilImovel;
	}

	public void setPerfilImovel(String perfilImovel) {
		this.perfilImovel = perfilImovel;
	}

}
