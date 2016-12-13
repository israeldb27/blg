/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.busqueumlugar.form;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import com.busqueumlugar.model.Notificacao;

/**
 *
 * @author israel.barreto
 */
public class NotificacaoForm extends BaseForm{
    
    private Long id;
    private long idUsuario;
    private BigInteger idImovel;
    private String statusLeitura;
    private String acao;
    private Date dataNotificacao;
    private String observacao;
    private String descricao;
    private String opcaoOrdenacao = "";
    private String tipoNotificacao;
    private String opcaoFiltro = "";    
    private long idUsuarioConvite;
    private String tituloImovel;
    


	public String getOpcaoFiltro() {
		return opcaoFiltro;
	}

	public void setOpcaoFiltro(String opcaoFiltro) {
		this.opcaoFiltro = opcaoFiltro;
	}

	public String getTipoNotificacao() {
		return tipoNotificacao;
	}

	public void setTipoNotificacao(String tipoNotificacao) {
		this.tipoNotificacao = tipoNotificacao;
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
     * @return the idImovel
     */
    public BigInteger getIdImovel() {
        return idImovel;
    }

    /**
     * @param idImovel the idImovel to set
     */
    public void setIdImovel(BigInteger idImovel) {
        this.idImovel = idImovel;
    }

    /**
     * @return the statusLeitura
     */
    public String getStatusLeitura() {
        return statusLeitura;
    }

    /**
     * @param statusLeitura the statusLeitura to set
     */
    public void setStatusLeitura(String statusLeitura) {
        this.statusLeitura = statusLeitura;
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
     * @return the dataNotificacao
     */
    public Date getDataNotificacao() {
        return dataNotificacao;
    }

    /**
     * @param dataNotificacao the dataNotificacao to set
     */
    public void setDataNotificacao(Date dataNotificacao) {
        this.dataNotificacao = dataNotificacao;
    }

    /**
     * @return the observacao
     */
    public String getObservacao() {
        return observacao;
    }

    /**
     * @param observacao the observacao to set
     */
    public void setObservacao(String observacao) {
        this.observacao = observacao;
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

	public long getIdUsuarioConvite() {
		return idUsuarioConvite;
	}

	public void setIdUsuarioConvite(long idUsuarioConvite) {
		this.idUsuarioConvite = idUsuarioConvite;
	}

	public String getTituloImovel() {
		return tituloImovel;
	}

	public void setTituloImovel(String tituloImovel) {
		this.tituloImovel = tituloImovel;
	}
    
}
