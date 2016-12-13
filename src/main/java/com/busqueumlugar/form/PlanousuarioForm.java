package com.busqueumlugar.form;

import java.util.Date;

/**
 *
 * @author Israel
 */
public class PlanousuarioForm extends BaseForm{
  
    private Long id;
    private long idPlano;
    private long idUsuario;
    private Date dataSolicitacao;
    private Date dataPagto;
    private String status;
    private long idImovel;
    private String nomePlano;
    private double valorPlano;
    private String formaPagto;
    private String imagemUsuario;
    
    
    public String getImagemUsuario() {
		return imagemUsuario;
	}

	public void setImagemUsuario(String imagemUsuario) {
		this.imagemUsuario = imagemUsuario;
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
     * @return the idPlano
     */
    public long getIdPlano() {
        return idPlano;
    }

    /**
     * @param idPlano the idPlano to set
     */
    public void setIdPlano(long idPlano) {
        this.idPlano = idPlano;
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
     * @return the dataSolicitacao
     */
    public Date getDataSolicitacao() {
        return dataSolicitacao;
    }

    /**
     * @param dataSolicitacao the dataSolicitacao to set
     */
    public void setDataSolicitacao(Date dataSolicitacao) {
        this.dataSolicitacao = dataSolicitacao;
    }

    /**
     * @return the dataPagto
     */
    public Date getDataPagto() {
        return dataPagto;
    }

    /**
     * @param dataPagto the dataPagto to set
     */
    public void setDataPagto(Date dataPagto) {
        this.dataPagto = dataPagto;
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
     * @return the idImovel
     */
    public long getIdImovel() {
        return idImovel;
    }

    /**
     * @param idImovel the idImovel to set
     */
    public void setIdImovel(long idImovel) {
        this.idImovel = idImovel;
    }

    /**
     * @return the nomePlano
     */
    public String getNomePlano() {
        return nomePlano;
    }

    /**
     * @param nomePlano the nomePlano to set
     */
    public void setNomePlano(String nomePlano) {
        this.nomePlano = nomePlano;
    }

    /**
     * @return the valorPlano
     */
    public double getValorPlano() {
        return valorPlano;
    }

    /**
     * @param valorPlano the valorPlano to set
     */
    public void setValorPlano(double valorPlano) {
        this.valorPlano = valorPlano;
    }

    /**
     * @return the formaPagto
     */
    public String getFormaPagto() {
        return formaPagto;
    }

    /**
     * @param formaPagto the formaPagto to set
     */
    public void setFormaPagto(String formaPagto) {
        this.formaPagto = formaPagto;
    }

}
