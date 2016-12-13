/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.busqueumlugar.form;

import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;

import com.busqueumlugar.model.Formapagamento;
import com.busqueumlugar.model.Infoservico;
import com.busqueumlugar.model.Usuario;


public class MensagemForm extends BaseForm{
    
    private Long id;
    private long idUsuarioDe;
    private long idUsuarioPara;
    private String descricao;    
    private String loginExibicao;
    private Date dataMensagem;
    private long idUsuarioExibicao;
    private String status;
    private String perfilUsuario;
    private String mensagemRetorno = "";
    
    private Usuario usuarioDe;
    private Usuario usuarioPara;
	
	private String formaPagtoSelecionado;
    private String nomeFormaPagto;
    private String opcao;
    private Long idServicoGerado;
    private String opcaoTempoPagto;
    private String labelServico;   
    private String idSimulaServicoMensagem = "";
    private String acao = "";
    private Long idUsuarioSelecionado;
	private double valorServico;
	private int tempoDuracao;
	private String tipoInfoServico;
	private int quantidadeServico;
	private String tipoServico;
	
	private List<Formapagamento> listaOpcoesFormaPagamento;
	private Long idFormapagamentoSelecionada;
    
    private List<Infoservico> listaInfoServicoPagamento;
    private Long idInfoServicoPagamentoSelecionada;
    
    private String entradaMensagem = "";    
    private String imagemUsuario;
    
    private String loginUsuarioDe;
    private String loginUsuarioPara;
    private String nomeUsuarioDe;
    private String nomeUsuarioPara;
    
    public String getEntradaMensagem() {
		return entradaMensagem;
	}

	public void setEntradaMensagem(String entradaMensagem) {
		this.entradaMensagem = entradaMensagem;
	}

	public String getTipoServico() {
		return tipoServico;
	}

	public void setTipoServico(String tipoServico) {
		this.tipoServico = tipoServico;
	}
    
    public String getTipoInfoServico() {
		return tipoInfoServico;
	}

	public void setTipoInfoServico(String tipoInfoServico) {
		this.tipoInfoServico = tipoInfoServico;
	}

	public int getQuantidadeServico() {
		return quantidadeServico;
	}

	public void setQuantidadeServico(int quantidadeServico) {
		this.quantidadeServico = quantidadeServico;
	}
    
    public double getValorServico() {
		return valorServico;
	}

	public void setValorServico(double valorServico) {
		this.valorServico = valorServico;
	}

	public int getTempoDuracao() {
		return tempoDuracao;
	}

	public void setTempoDuracao(int tempoDuracao) {
		this.tempoDuracao = tempoDuracao;
	}

	public Long getIdUsuarioSelecionado() {
		return idUsuarioSelecionado;
	}

	public void setIdUsuarioSelecionado(Long idUsuarioSelecionado) {
		this.idUsuarioSelecionado = idUsuarioSelecionado;
	}
    
    public String getAcao() {
		return acao;
	}

	public void setAcao(String acao) {
		this.acao = acao;
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

	public String getOpcao() {
		return opcao;
	}

	public void setOpcao(String opcao) {
		this.opcao = opcao;
	}

	public Long getIdServicoGerado() {
		return idServicoGerado;
	}

	public void setIdServicoGerado(Long idServicoGerado) {
		this.idServicoGerado = idServicoGerado;
	}

	public String getOpcaoTempoPagto() {
		return opcaoTempoPagto;
	}

	public void setOpcaoTempoPagto(String opcaoTempoPagto) {
		this.opcaoTempoPagto = opcaoTempoPagto;
	}

	public String getLabelServico() {
		return labelServico;
	}

	public void setLabelServico(String labelServico) {
		this.labelServico = labelServico;
	}

	public String getIdSimulaServicoMensagem() {
		return idSimulaServicoMensagem;
	}

	public void setIdSimulaServicoMensagem(String idSimulaServicoMensagem) {
		this.idSimulaServicoMensagem = idSimulaServicoMensagem;
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
     * @return the idUsuarioDe
     */
    public long getIdUsuarioDe() {
        return idUsuarioDe;
    }

    /**
     * @param idUsuarioDe the idUsuarioDe to set
     */
    public void setIdUsuarioDe(long idUsuarioDe) {
        this.idUsuarioDe = idUsuarioDe;
    }

    /**
     * @return the idUsuarioPara
     */
    public long getIdUsuarioPara() {
        return idUsuarioPara;
    }

    /**
     * @param idUsuarioPara the idUsuarioPara to set
     */
    public void setIdUsuarioPara(long idUsuarioPara) {
        this.idUsuarioPara = idUsuarioPara;
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
     * @return the loginExibicao
     */
    public String getLoginExibicao() {
        return loginExibicao;
    }

    /**
     * @param loginExibicao the loginExibicao to set
     */
    public void setLoginExibicao(String loginExibicao) {
        this.loginExibicao = loginExibicao;
    }

    /**
     * @return the dataMensagem
     */
    public Date getDataMensagem() {
        return dataMensagem;
    }

    /**
     * @param dataMensagem the dataMensagem to set
     */
    public void setDataMensagem(Date dataMensagem) {
        this.dataMensagem = dataMensagem;
    }

    /**
     * @return the idUsuarioExibicao
     */
    public long getIdUsuarioExibicao() {
        return idUsuarioExibicao;
    }

    /**
     * @param idUsuarioExibicao the idUsuarioExibicao to set
     */
    public void setIdUsuarioExibicao(long idUsuarioExibicao) {
        this.idUsuarioExibicao = idUsuarioExibicao;
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
     * @return the perfilUsuario
     */
    public String getPerfilUsuario() {
        return perfilUsuario;
    }

    /**
     * @param perfilUsuario the perfilUsuario to set
     */
    public void setPerfilUsuario(String perfilUsuario) {
        this.perfilUsuario = perfilUsuario;
    }
   
    /**
     * @return the mensagemRetorno
     */
    public String getMensagemRetorno() {
        return mensagemRetorno;
    }

    /**
     * @param mensagemRetorno the mensagemRetorno to set
     */
    public void setMensagemRetorno(String mensagemRetorno) {
        this.mensagemRetorno = mensagemRetorno;
    }

	public String getImagemUsuario() {
		return imagemUsuario;
	}

	public void setImagemUsuario(String imagemUsuario) {
		this.imagemUsuario = imagemUsuario;
	}

	public String getLoginUsuarioDe() {
		return loginUsuarioDe;
	}

	public void setLoginUsuarioDe(String loginUsuarioDe) {
		this.loginUsuarioDe = loginUsuarioDe;
	}

	public String getLoginUsuarioPara() {
		return loginUsuarioPara;
	}

	public void setLoginUsuarioPara(String loginUsuarioPara) {
		this.loginUsuarioPara = loginUsuarioPara;
	}

	public String getNomeUsuarioDe() {
		return nomeUsuarioDe;
	}

	public void setNomeUsuarioDe(String nomeUsuarioDe) {
		this.nomeUsuarioDe = nomeUsuarioDe;
	}

	public String getNomeUsuarioPara() {
		return nomeUsuarioPara;
	}

	public void setNomeUsuarioPara(String nomeUsuarioPara) {
		this.nomeUsuarioPara = nomeUsuarioPara;
	}

	public Usuario getUsuarioDe() {
		return usuarioDe;
	}

	public void setUsuarioDe(Usuario usuarioDe) {
		this.usuarioDe = usuarioDe;
	}

	public Usuario getUsuarioPara() {
		return usuarioPara;
	}

	public void setUsuarioPara(Usuario usuarioPara) {
		this.usuarioPara = usuarioPara;
	}
    
}
