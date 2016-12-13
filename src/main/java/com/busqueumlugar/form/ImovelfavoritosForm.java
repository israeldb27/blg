/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.busqueumlugar.form;

import java.util.Date;
import java.util.List;
import com.busqueumlugar.enumerador.AcaoImovelEnum;
import com.busqueumlugar.enumerador.TipoImovelEnum;
import com.busqueumlugar.util.Select;
import com.mysql.jdbc.StringUtils;
/**
 *
 * @author isbarr
 */
public class ImovelfavoritosForm extends BaseForm {    
    
    private Long id;    
    private Long idUsuario;    
    private Long idImovel;    
    private Long idDonoImovel;        
    private Date dataInteresse;
    private String dataInteresseFmt;        
    private String status; // novo, lido    
    
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
    private String tipoLista = "";
    private String opcaoVisualizacao = "";
    private String opcaoOrdenacao = "";    
    private String[] idUsuariosSelecionados;

    private int idEstadoAgruparImoveis;
    private int idCidadeAgruparImoveis;
    private int idBairroAgruparImoveis;
    
    private String acaoAgruparImoveis;
    private String tipoImovelAgruparImoveis;
    
    private String opcaoContatoAgruparUsuarios;
    private boolean temFiltroContatoAgruparUsuarios;    
    private String opcaoPerfilContatoAgruparUsuarios;
    private boolean temFiltroPerfilContatoAgruparUsuarios;
	
	private String tipoImovelFmt = "";    
    private String 	acaoFmt;
    
    private String valorMin;
	private String valorMax;
	private int quantQuartos = 0;      
    private int quantGaragem = 0;      
    private int quantSuites = 0;        
    private int quantBanheiro = 0; 
    private String perfilImovel;

	public String[] getIdUsuariosSelecionados() {
		return idUsuariosSelecionados;
	}

	public void setIdUsuariosSelecionados(String[] idUsuariosSelecionados) {
		this.idUsuariosSelecionados = idUsuariosSelecionados;
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

	public String getOpcaoOrdenacao() {
		return opcaoOrdenacao;
	}

	public void setOpcaoOrdenacao(String opcaoOrdenacao) {
		this.opcaoOrdenacao = opcaoOrdenacao;
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
     * @return the idImovel
     */
    public Long getIdImovel() {
        return idImovel;
    }

    /**
     * @param idImovel the idImovel to set
     */
    public void setIdImovel(Long idImovel) {
        this.idImovel = idImovel;
    }

    /**
     * @return the idDonoImovel
     */
    public Long getIdDonoImovel() {
        return idDonoImovel;
    }

    /**
     * @param idDonoImovel the idDonoImovel to set
     */
    public void setIdDonoImovel(Long idDonoImovel) {
        this.idDonoImovel = idDonoImovel;
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
     * @return the dataInteresse
     */
    public Date getDataInteresse() {
        return dataInteresse;
    }

    /**
     * @param dataInteresse the dataInteresse to set
     */
    public void setDataInteresse(Date dataInteresse) {
        this.dataInteresse = dataInteresse;
    }

    /**
     * @return the dataInteresseFmt
     */
    public String getDataInteresseFmt() {
        return dataInteresseFmt;
    }

    /**
     * @param dataInteresseFmt the dataInteresseFmt to set
     */
    public void setDataInteresseFmt(String dataInteresseFmt) {
        this.dataInteresseFmt = dataInteresseFmt;
    }
	
	public String getTipoImovelFmt() {
		return TipoImovelEnum.getLabel(this.tipoImovel); 
	}

	public void setTipoImovelFmt(String tipoImovelFmt) {
		this.tipoImovelFmt = tipoImovelFmt;
	}

	public String getOpcaoContatoAgruparUsuarios() {
		return opcaoContatoAgruparUsuarios;
	}

	public void setOpcaoContatoAgruparUsuarios(String opcaoContatoAgruparUsuarios) {
		this.opcaoContatoAgruparUsuarios = opcaoContatoAgruparUsuarios;
	}

	public String getOpcaoPerfilContatoAgruparUsuarios() {
		return opcaoPerfilContatoAgruparUsuarios;
	}

	public void setOpcaoPerfilContatoAgruparUsuarios(
			String opcaoPerfilContatoAgruparUsuarios) {
		this.opcaoPerfilContatoAgruparUsuarios = opcaoPerfilContatoAgruparUsuarios;
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

	public String getAcaoFmt() {
		return AcaoImovelEnum.getLabel(this.acao);
	}

	public void setAcaoFmt(String acaoFmt) {
		this.acaoFmt = acaoFmt;
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
