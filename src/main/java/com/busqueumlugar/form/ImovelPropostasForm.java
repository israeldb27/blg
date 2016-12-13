package com.busqueumlugar.form;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Lob;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.busqueumlugar.enumerador.TipoImovelEnum;
import com.busqueumlugar.model.Imovel;
import com.busqueumlugar.model.ImovelPropostas;
import com.busqueumlugar.model.Usuario;
import com.busqueumlugar.util.Select;
import com.mysql.jdbc.StringUtils;

public class ImovelPropostasForm extends BaseForm{

	private Long id;    
    private BigDecimal valorProposta;
    private Date dataCadastro;
    private Long idUsuarioLancador;
    private Usuario usuarioLancador;
    private Imovel imovel;
    private Usuario usuarioReceptor;     
    private String status; // nova, lida
    private String tipoFiltro = "";
    private String observacao;         
    private String imagemArquivo;
    private String tipoImovel;
    private String acao;
  
	private List<Select> listaEstados;
    private List<Select> listaCidades;
    private List<Select> listaBairros;
    
    private int idEstado;  
    private int idCidade;
    private int idBairro;   
    
    private String opcaoOrdenacao = "";
    private String opcaoVisualizacao = "";
    private String tipoLista = "";
    
    private int idEstadoAgruparImoveis;
    private int idCidadeAgruparImoveis;
    private int idBairroAgruparImoveis;
    
    private String acaoAgruparImoveis;
    private String tipoImovelAgruparImoveis;
    
    private String opcaoContatoAgruparUsuarios;
    private boolean temFiltroContatoAgruparUsuarios;
    
    private String opcaoPerfilContatoAgruparUsuarios;
    private boolean temFiltroPerfilContatoAgruparUsuarios;
    
    private String valorMin;
   	private String valorMax;
   	private int quantQuartos = 0;      
    private int quantGaragem = 0;      
    private int quantSuites = 0;        
    private int quantBanheiro = 0; 
    private String perfilImovel;
    
	
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
	public String getOpcaoOrdenacao() {
		return opcaoOrdenacao;
	}
	public void setOpcaoOrdenacao(String opcaoOrdenacao) {
		this.opcaoOrdenacao = opcaoOrdenacao;
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
	public String getTipoFiltro() {
		return tipoFiltro;
	}
	public void setTipoFiltro(String tipoFiltro) {
		this.tipoFiltro = tipoFiltro;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public BigDecimal getValorProposta() {
		return valorProposta;
	}
	public void setValorProposta(BigDecimal valorProposta) {
		this.valorProposta = valorProposta;
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
	
	public String getImagemArquivo() {
		return imagemArquivo;
	}
	public void setImagemArquivo(String imagemArquivo) {
		this.imagemArquivo = imagemArquivo;
	}
	
    public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
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
	public Usuario getUsuarioLancador() {
		return usuarioLancador;
	}
	public void setUsuarioLancador(Usuario usuarioLancador) {
		this.usuarioLancador = usuarioLancador;
	}
	public Imovel getImovel() {
		return imovel;
	}
	public void setImovel(Imovel imovel) {
		this.imovel = imovel;
	}
	public Usuario getUsuarioReceptor() {
		return usuarioReceptor;
	}
	public void setUsuarioReceptor(Usuario usuarioReceptor) {
		this.usuarioReceptor = usuarioReceptor;
	}
	public Long getIdUsuarioLancador() {
		return idUsuarioLancador;
	}
	public void setIdUsuarioLancador(Long idUsuarioLancador) {
		this.idUsuarioLancador = idUsuarioLancador;
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

