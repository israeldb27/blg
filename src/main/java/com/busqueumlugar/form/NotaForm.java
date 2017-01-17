/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.busqueumlugar.form;

import java.math.BigInteger;
import java.util.Date;

/**
 *
 * @author Israel
 */
public class NotaForm extends BaseForm{
    
    private Long id;
    private BigInteger idImovel;    
    private BigInteger idUsuario;
    private Date dataNota;
    private String descricao;
    private String acao;
    private String descMinhaNota;    
    private String opcaoOrdenacao = "";
    private String opcaoFiltro = "";
    private String tipoLista = "";
    private String escreverNota = "";
    

    public String getOpcaoFiltro() {
		return opcaoFiltro;
	}

	public void setOpcaoFiltro(String opcaoFiltro) {
		this.opcaoFiltro = opcaoFiltro;
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
     * @return the idUsuario
     */
    public BigInteger getIdUsuario() {
        return idUsuario;
    }

    /**
     * @param idUsuario the idUsuario to set
     */
    public void setIdUsuario(BigInteger idUsuario) {
        this.idUsuario = idUsuario;
    }

    /**
     * @return the dataNota
     */
    public Date getDataNota() {
        return dataNota;
    }

    /**
     * @param dataNota the dataNota to set
     */
    public void setDataNota(Date dataNota) {
        this.dataNota = dataNota;
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
     * @return the descMinhaNota
     */
    public String getDescMinhaNota() {
        return descMinhaNota;
    }

    /**
     * @param descMinhaNota the descMinhaNota to set
     */
    public void setDescMinhaNota(String descMinhaNota) {
        this.descMinhaNota = descMinhaNota;
    }

	public String getEscreverNota() {
		return escreverNota;
	}

	public void setEscreverNota(String escreverNota) {
		this.escreverNota = escreverNota;
	}
    
}
