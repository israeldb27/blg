package com.busqueumlugar.form;

import java.util.Date;
import java.util.List;
import com.busqueumlugar.model.Imovel;
import com.busqueumlugar.model.Imovelcomentario;
import com.busqueumlugar.model.Usuario;
import com.busqueumlugar.util.Select;

public class ImovelcomentarioForm extends BaseForm{
	
		private Long id;	    
	    private Date dataComentario;
	    private String comentario;
	    private long idUsuarioComentario;
	    private long idImovel;
	    private long idUsuario;	
	    private String status; // novo, lido
	    private Date dataCadastro;	
	    private Date dataUltimoComentario;
	    private String descUltimoComentario;
	    private String novoComentario = "";
	    private String tipoLista = "";
	    private String opcaoOrdenacao = "";
	    private String opcaoVisualizacao = "";
	    
	    private List<Select> listaEstados;
	    private List<Select> listaCidades;
	    private List<Select> listaBairros;
	    
	    private int idEstado;  
	    private int idCidade;
	    private int idBairro;
	    
	    private String tipoImovel = "";
	    private String acao = "";
	    
	   // private List<Imovelcomentario> listaComentariosRecebidos;
	    private List<Usuario> listaComentariosRecebidosAgruparUsuarios;
	    private List<Imovel> listaComentariosRecebidosAgruparImoveis;
	    
	//    private List<Imovelcomentario> listaComentariosEnviados;
	    private List<Usuario> listaComentariosEnviadosAgruparUsuarios;
	    private List<Imovel> listaComentariosEnviadosAgruparImoveis;
	    
	    private List<Imovelcomentario> listaTodosComentarios;

	    
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

		public List<Usuario> getListaComentariosRecebidosAgruparUsuarios() {
			return listaComentariosRecebidosAgruparUsuarios;
		}

		public void setListaComentariosRecebidosAgruparUsuarios(
				List<Usuario> listaComentariosRecebidosAgruparUsuarios) {
			this.listaComentariosRecebidosAgruparUsuarios = listaComentariosRecebidosAgruparUsuarios;
		}

		public List<Usuario> getListaComentariosEnviadosAgruparUsuarios() {
			return listaComentariosEnviadosAgruparUsuarios;
		}

		public void setListaComentariosEnviadosAgruparUsuarios(
				List<Usuario> listaComentariosEnviadosAgruparUsuarios) {
			this.listaComentariosEnviadosAgruparUsuarios = listaComentariosEnviadosAgruparUsuarios;
		}

		public List<Imovel> getListaComentariosRecebidosAgruparImoveis() {
			return listaComentariosRecebidosAgruparImoveis;
		}

		public void setListaComentariosRecebidosAgruparImoveis(
				List<Imovel> listaComentariosRecebidosAgruparImoveis) {
			this.listaComentariosRecebidosAgruparImoveis = listaComentariosRecebidosAgruparImoveis;
		}
		

		public List<Imovel> getListaComentariosEnviadosAgruparImoveis() {
			return listaComentariosEnviadosAgruparImoveis;
		}

		public void setListaComentariosEnviadosAgruparImoveis(
				List<Imovel> listaComentariosEnviadosAgruparImoveis) {
			this.listaComentariosEnviadosAgruparImoveis = listaComentariosEnviadosAgruparImoveis;
		}
/*
		public List<Imovelcomentario> getListaComentariosEnviados() {
			return listaComentariosEnviados;
		}

		public void setListaComentariosEnviados(
				List<Imovelcomentario> listaComentariosEnviados) {
			this.listaComentariosEnviados = listaComentariosEnviados;
		}
*/
	/*	public List<Imovelcomentario> getListaComentariosRecebidos() {
			return listaComentariosRecebidos;
		}

		public void setListaComentariosRecebidos(
				List<Imovelcomentario> listaComentariosRecebidos) {
			this.listaComentariosRecebidos = listaComentariosRecebidos;
		}
*/
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

		public String getNovoComentario() {
			return novoComentario;
		}

		public void setNovoComentario(String novoComentario) {
			this.novoComentario = novoComentario;
		}

		public Long getId() {
	        return id;
	    }

	    public void setId(Long id) {
	        this.id = id;
	    }

	    public Date getDataComentario() {
	        return dataComentario;
	    }

	    public void setDataComentario(Date dataComentario) {
	        this.dataComentario = dataComentario;
	    }

	    public String getComentario() {
	        return comentario;
	    }

	    public void setComentario(String comentario) {
	        this.comentario = comentario;
	    }

	    public long getIdImovel() {
	        return idImovel;
	    }

	    public void setIdImovel(long idImovel) {
	        this.idImovel = idImovel;
	    }

	    public long getIdUsuarioComentario() {
	        return idUsuarioComentario;
	    }

	    public void setIdUsuarioComentario(long idUsuarioComentario) {
	        this.idUsuarioComentario = idUsuarioComentario;
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
	     * @return the dataUltimoComentario
	     */
	    public Date getDataUltimoComentario() {
	        return dataUltimoComentario;
	    }

	    /**
	     * @param dataUltimoComentario the dataUltimoComentario to set
	     */
	    public void setDataUltimoComentario(Date dataUltimoComentario) {
	        this.dataUltimoComentario = dataUltimoComentario;
	    }

	    /**
	     * @return the descUltimoComentario
	     */
	    public String getDescUltimoComentario() {
	        return descUltimoComentario;
	    }

	    /**
	     * @param descUltimoComentario the descUltimoComentario to set
	     */
	    public void setDescUltimoComentario(String descUltimoComentario) {
	        this.descUltimoComentario = descUltimoComentario;
	    }

		public List<Imovelcomentario> getListaTodosComentarios() {
			return listaTodosComentarios;
		}

		public void setListaTodosComentarios(
				List<Imovelcomentario> listaTodosComentarios) {
			this.listaTodosComentarios = listaTodosComentarios;
		}

}
