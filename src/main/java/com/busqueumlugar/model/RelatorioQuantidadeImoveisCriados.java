package com.busqueumlugar.model;

import java.io.Serializable;

import com.busqueumlugar.enumerador.AcaoImovelEnum;
import com.busqueumlugar.enumerador.StatusImovelEnum;
import com.busqueumlugar.enumerador.TipoImovelEnum;
import com.mysql.jdbc.StringUtils;

public class RelatorioQuantidadeImoveisCriados implements Serializable {
    private static final long serialVersionUID = 1L;

    private String acao = "";
    private String tipoImovel = "";
    private String perfilImovel = "";
    private int quantidade;
    
    private String estado = "";
    private String cidade = "";
    private String bairro = "";
	
    private String tipoImovelFmt = "";
	private String acaoFmt = "";
    private String perfilImovelFmt = ""; 
    

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
     * @return the tipoImovel
     */
    public String getTipoImovel() {
        return tipoImovel;
    }

    /**
     * @param tipoImovel the tipoImovel to set
     */
    public void setTipoImovel(String tipoImovel) {
        this.tipoImovel = tipoImovel;
    }

    /**
     * @return the quantidade
     */
    public int getQuantidade() {
        return quantidade;
    }

    /**
     * @param quantidade the quantidade to set
     */
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    /**
     * @return the cidade
     */
    public String getCidade() {
        return cidade;
    }

    /**
     * @param cidade the cidade to set
     */
    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    /**
     * @return the estado
     */
    public String getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * @return the bairro
     */
    public String getBairro() {
        return bairro;
    }

    /**
     * @param bairro the bairro to set
     */
    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    /**
     * @return the perfilImovel
     */
    public String getPerfilImovel() {
        return perfilImovel;
    }

    /**
     * @param perfilImovel the perfilImovel to set
     */
    public void setPerfilImovel(String perfilImovel) {
        this.perfilImovel = perfilImovel;
    }
	
	public String getTipoImovelFmt() {
		if (! StringUtils.isNullOrEmpty(this.tipoImovel))
			return TipoImovelEnum.getLabel(this.tipoImovel);
		else
			return "";
		 
	}

	public void setTipoImovelFmt(String tipoImovelFmt) {
		this.tipoImovelFmt = tipoImovelFmt;
	}
	
	public String getAcaoFmt() {
		if (! StringUtils.isNullOrEmpty(this.acao))
			return  AcaoImovelEnum.getLabel(this.acao);
		else
			return "";
	}

	public void setAcaoFmt(String acaoFmt) {
		this.acaoFmt = acaoFmt;
	}

	public String getPerfilImovelFmt() {
		if (! StringUtils.isNullOrEmpty(perfilImovel))
			return  StatusImovelEnum.getLabel(this.perfilImovel);
		else
			return "";
	}

	public void setPerfilImovelFmt(String perfilImovelFmt) {
		this.perfilImovelFmt = perfilImovelFmt;
	}
    
    
}
