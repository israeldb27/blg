package com.busqueumlugar.model;

public class RelatorioQuantAssinatura {
    
    private String tipoAssinatura = "";
    private double valorFinanceiro = 0d;
    private String nomeUsuario = "";
    private int quantidade = 0;

    /**
     * @return the tipoAssinatura
     */
    public String getTipoAssinatura() {
        return tipoAssinatura;
    }

    /**
     * @param tipoAssinatura the tipoAssinatura to set
     */
    public void setTipoAssinatura(String tipoAssinatura) {
        this.tipoAssinatura = tipoAssinatura;
    }

    /**
     * @return the valorFinanceiro
     */
    public double getValorFinanceiro() {
        return valorFinanceiro;
    }

    /**
     * @param valorFinanceiro the valorFinanceiro to set
     */
    public void setValorFinanceiro(double valorFinanceiro) {
        this.valorFinanceiro = valorFinanceiro;
    }

    /**
     * @return the nomeUsuario
     */
    public String getNomeUsuario() {
        return nomeUsuario;
    }

    /**
     * @param nomeUsuario the nomeUsuario to set
     */
    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
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
    
}
