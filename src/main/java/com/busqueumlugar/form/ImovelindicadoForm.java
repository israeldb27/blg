package com.busqueumlugar.form;

import java.util.Date;
import java.util.List;

import javax.persistence.Transient;

import com.busqueumlugar.enumerador.TipoImovelEnum;
import com.busqueumlugar.model.Imovel;
import com.busqueumlugar.model.Imovelindicado;
import com.busqueumlugar.model.Usuario;
import com.busqueumlugar.util.Select;
import com.mysql.jdbc.StringUtils;

public class ImovelindicadoForm extends BaseForm{

 	private Long id;
    private String observacao;
    private long idImovel;
    private long idUsuario;
    private long idUsuarioIndicador;    
    private String statusIndicado;
    private Date dataIndicacao; // criar este campo no banco ainda

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
    private String opcaoFiltro = "";
    private String tipoLista = "";
    private String opcaoVisualizacao = "";
    
    private List<Usuario> listaUsuariosContatos;
    private String emailIndicado = "";
    
    private String[] idUsuariosSelecionados;   

    private List<Usuario> listaTodosContatos;
	
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

	public String getOpcaoFiltro() {
		return opcaoFiltro;
	}
	public void setOpcaoFiltro(String opcaoFiltro) {
		this.opcaoFiltro = opcaoFiltro;
	}
	public String[] getIdUsuariosSelecionados() {
		return idUsuariosSelecionados;
	}
	public void setIdUsuariosSelecionados(String[] idUsuariosSelecionados) {
		this.idUsuariosSelecionados = idUsuariosSelecionados;
	}
	public List<Usuario> getListaUsuariosContatos() {
		return listaUsuariosContatos;
	}
	public void setListaUsuariosContatos(List<Usuario> listaUsuariosContatos) {
		this.listaUsuariosContatos = listaUsuariosContatos;
	}
	public String getEmailIndicado() {
		return emailIndicado;
	}
	public void setEmailIndicado(String emailIndicado) {
		this.emailIndicado = emailIndicado;
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
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	public long getIdImovel() {
		return idImovel;
	}
	public void setIdImovel(long idImovel) {
		this.idImovel = idImovel;
	}
	public long getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(long idUsuario) {
		this.idUsuario = idUsuario;
	}
	public long getIdUsuarioIndicador() {
		return idUsuarioIndicador;
	}
	public void setIdUsuarioIndicador(long idUsuarioIndicador) {
		this.idUsuarioIndicador = idUsuarioIndicador;
	}	
	public String getStatusIndicado() {
		return statusIndicado;
	}
	public void setStatusIndicado(String statusIndicado) {
		this.statusIndicado = statusIndicado;
	}
	public Date getDataIndicacao() {
		return dataIndicacao;
	}
	public void setDataIndicacao(Date dataIndicacao) {
		this.dataIndicacao = dataIndicacao;
	}
	public String getIndicacaoEmail() {
		return indicacaoEmail;
	}
	public void setIndicacaoEmail(String indicacaoEmail) {
		this.indicacaoEmail = indicacaoEmail;
	}
	public String getEmailImovelIndicado() {
		return emailImovelIndicado;
	}
	public void setEmailImovelIndicado(String emailImovelIndicado) {
		this.emailImovelIndicado = emailImovelIndicado;
	}
	private String indicacaoEmail; // S, N	    
    private String emailImovelIndicado;

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

    public String getOpcaoVisualizacao() {
        return opcaoVisualizacao;
    }

    public void setOpcaoVisualizacao(String opcaoVisualizacao) {
        this.opcaoVisualizacao = opcaoVisualizacao;
    }
	public List<Usuario> getListaTodosContatos() {
		return listaTodosContatos;
	}
	public void setListaTodosContatos(List<Usuario> listaTodosContatos) {
		this.listaTodosContatos = listaTodosContatos;
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
