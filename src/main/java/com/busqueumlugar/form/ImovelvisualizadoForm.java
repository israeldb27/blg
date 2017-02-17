package com.busqueumlugar.form;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.mysql.jdbc.StringUtils;
import com.busqueumlugar.enumerador.TipoImovelEnum;
import com.busqueumlugar.model.Imovel;
import com.busqueumlugar.model.Imovelvisualizado;
import com.busqueumlugar.model.Usuario;
import com.busqueumlugar.util.Select;

public class ImovelvisualizadoForm extends BaseForm{

	private Long id;    
	private Imovel imovel;
    private Usuario usuario;    
    private Date dataVisita;
    private int idEstado;
    private int idCidade;
    private int idBairro;
    private String tipoImovel;
    private String acao;
    private String statusVisita;    
    private Usuario usuarioDonoImovel;    
    private List<Select> listaEstados;
    private List<Select> listaCidades;
    private List<Select> listaBairros;
	
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
    
    private String interessadoImovel = ""; // este campo eh usado para indicar se o usuario esta interessado ou nao no imovel
    
    private String valorMin;
	private String valorMax;
	private int quantQuartos = 0;      
    private int quantGaragem = 0;      
    private int quantSuites = 0;        
    private int quantBanheiro = 0;
    private String perfilImovel;
	

	public String getInteressadoImovel() {
		return interessadoImovel;
	}
	public void setInteressadoImovel(String interessadoImovel) {
		this.interessadoImovel = interessadoImovel;
	}
	public String getAcao() {
		return acao;
	}
	public void setAcao(String acao) {
		this.acao = acao;
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
	public String getOpcaoVisualizacao() {
		return opcaoVisualizacao;
	}
	public void setOpcaoVisualizacao(String opcaoVisualizacao) {
		this.opcaoVisualizacao = opcaoVisualizacao;
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
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getDataVisita() {
		return dataVisita;
	}
	public void setDataVisita(Date dataVisita) {
		this.dataVisita = dataVisita;
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

	public String getStatusVisita() {
		return statusVisita;
	}
	public void setStatusVisita(String statusVisita) {
		this.statusVisita = statusVisita;
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
		if (!StringUtils.isNullOrEmpty(this.opcaoContatoAgruparUsuarios))
			return true;
		else
			return false;	
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
		if (!StringUtils.isNullOrEmpty(this.opcaoPerfilContatoAgruparUsuarios))
			return true;
		else
			return false;	
	}
	public void setTemFiltroPerfilContatoAgruparUsuarios(
			boolean temFiltroPerfilContatoAgruparUsuarios) {
		this.temFiltroPerfilContatoAgruparUsuarios = temFiltroPerfilContatoAgruparUsuarios;
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
	public Usuario getUsuarioDonoImovel() {
		return usuarioDonoImovel;
	}
	public void setUsuarioDonoImovel(Usuario usuarioDonoImovel) {
		this.usuarioDonoImovel = usuarioDonoImovel;
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
