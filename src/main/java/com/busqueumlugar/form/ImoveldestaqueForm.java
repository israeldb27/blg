package com.busqueumlugar.form;

import java.util.Date;
import java.util.List;

import com.busqueumlugar.util.Select;


public class ImoveldestaqueForm extends BaseForm {
	
	private Long id;   
    private Date dataDestaque;
    private Date dataCadastro;
    private String status;   // criado, cancelado, suspenso
    private String tipo; // anuncio, indicacao    	
    
    private String dataInicioCadastro;
    private String dataFimCadastro;
    
    private String dataInicioAnuncio;
    private String dataFimAnuncio;
    
    private List<Select> listaEstados;
    private List<Select> listaCidades;
    private List<Select> listaBairros;
    
    private int idEstado = 0;  
    private int idCidade = 0;
    private int idBairro = 0;
    
    private String tipoImovel = "";
    private String acao = "";
    private String perfilImovel = "";
    
    private int quantQuartos = 0;      
    private int quantGaragem = 0;      
    private int quantSuites = 0;        
    private int quantBanheiro = 0;
    
    private String opcaoOrdenacao;
    private String opcaoPaginacao;
    
    private String tipoImovelFmt = "";    
    private String acaoFmt = "";
    private String perfilImovelFmt = "";
    

    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getDataDestaque() {
		return dataDestaque;
	}
	public void setDataDestaque(Date dataDestaque) {
		this.dataDestaque = dataDestaque;
	}
	public Date getDataCadastro() {
		return dataCadastro;
	}
	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getDataInicioCadastro() {
		return dataInicioCadastro;
	}
	public void setDataInicioCadastro(String dataInicioCadastro) {
		this.dataInicioCadastro = dataInicioCadastro;
	}
	public String getDataFimCadastro() {
		return dataFimCadastro;
	}
	public void setDataFimCadastro(String dataFimCadastro) {
		this.dataFimCadastro = dataFimCadastro;
	}
	public String getDataInicioAnuncio() {
		return dataInicioAnuncio;
	}
	public void setDataInicioAnuncio(String dataInicioAnuncio) {
		this.dataInicioAnuncio = dataInicioAnuncio;
	}
	public String getDataFimAnuncio() {
		return dataFimAnuncio;
	}
	public void setDataFimAnuncio(String dataFimAnuncio) {
		this.dataFimAnuncio = dataFimAnuncio;
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
	public String getPerfilImovel() {
		return perfilImovel;
	}
	public void setPerfilImovel(String perfilImovel) {
		this.perfilImovel = perfilImovel;
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
	public String getOpcaoOrdenacao() {
		return opcaoOrdenacao;
	}
	public void setOpcaoOrdenacao(String opcaoOrdenacao) {
		this.opcaoOrdenacao = opcaoOrdenacao;
	}
	public String getOpcaoPaginacao() {
		return opcaoPaginacao;
	}
	public void setOpcaoPaginacao(String opcaoPaginacao) {
		this.opcaoPaginacao = opcaoPaginacao;
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
		return perfilImovelFmt;
	}
	public void setPerfilImovelFmt(String perfilImovelFmt) {
		this.perfilImovelFmt = perfilImovelFmt;
	}
	

}
