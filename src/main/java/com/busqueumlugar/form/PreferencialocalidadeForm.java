package com.busqueumlugar.form;

import java.util.List;







import javax.persistence.Column;

import com.busqueumlugar.enumerador.AcaoImovelEnum;
import com.busqueumlugar.util.Select;

public class PreferencialocalidadeForm extends BaseForm{
	
	private Long id;
	private int idEstado = 0;
    private int idCidade = 0;
    private int idBairro = 0;
    private long idUsuario;
    private String nomeEstado = "";
    private String nomeCidade = "";
    private String nomeBairro = "";
    private String tipoImovel = "";
    private String acao = "";
    private int quantQuartos = 0;
    private int quantGaragem = 0;      
    private int quantSuites = 0;
    private int quantBanheiro = 0;
    private String perfilImovel;	
	private String estado;

    private String acaoFmt = "";
    
    private List<Select> listaEstados;
    private List<Select> listaCidades;
    private List<Select> listaBairros;
    
    
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
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public long getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(long idUsuario) {
		this.idUsuario = idUsuario;
	}
	public String getNomeEstado() {
		return nomeEstado;
	}
	public void setNomeEstado(String nomeEstado) {
		this.nomeEstado = nomeEstado;
	}
	public String getNomeCidade() {
		return nomeCidade;
	}
	public void setNomeCidade(String nomeCidade) {
		this.nomeCidade = nomeCidade;
	}
	public String getNomeBairro() {
		return nomeBairro;
	}
	public void setNomeBairro(String nomeBairro) {
		this.nomeBairro = nomeBairro;
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
	public String getAcaoFmt() {
		return  AcaoImovelEnum.getLabel(this.acao);
	}
	public void setAcaoFmt(String acaoFmt) {
		this.acaoFmt = acaoFmt;
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
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
}
