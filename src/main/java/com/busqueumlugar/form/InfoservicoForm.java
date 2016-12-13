package com.busqueumlugar.form;

/**
 *
 * @author Israel
 */
public class InfoservicoForm {
    
    
    private Long id;
    private long idParamServico;
    private String labelDuracao;
    private double valorServico;
    private int tempoDuracao;
    private String itemInfoServico;   
    private int quantidade;    
    private String tipoInfoServico;

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
     * @return the idParamServico
     */
    public long getIdParamServico() {
        return idParamServico;
    }

    /**
     * @param idParamServico the idParamServico to set
     */
    public void setIdParamServico(long idParamServico) {
        this.idParamServico = idParamServico;
    }

    /**
     * @return the labelDuracao
     */
    public String getLabelDuracao() {
        return labelDuracao;
    }

    /**
     * @param labelDuracao the labelDuracao to set
     */
    public void setLabelDuracao(String labelDuracao) {
        this.labelDuracao = labelDuracao;
    }

    /**
     * @return the valorServico
     */
    public double getValorServico() {
        return valorServico;
    }

    /**
     * @param valorServico the valorServico to set
     */
    public void setValorServico(double valorServico) {
        this.valorServico = valorServico;
    }

    /**
     * @return the tempoDuracao
     */
    public int getTempoDuracao() {
        return tempoDuracao;
    }

    /**
     * @param tempoDuracao the tempoDuracao to set
     */
    public void setTempoDuracao(int tempoDuracao) {
        this.tempoDuracao = tempoDuracao;
    }

    /**
     * @return the itemInfoServico
     */
    public String getItemInfoServico() {
        return itemInfoServico;
    }

    /**
     * @param itemInfoServico the itemInfoServico to set
     */
    public void setItemInfoServico(String itemInfoServico) {
        this.itemInfoServico = itemInfoServico;
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
     * @return the tipoInfoServico
     */
    public String getTipoInfoServico() {
        return tipoInfoServico;
    }

    /**
     * @param tipoInfoServico the tipoInfoServico to set
     */
    public void setTipoInfoServico(String tipoInfoServico) {
        this.tipoInfoServico = tipoInfoServico;
    }

        
}
