package com.busqueumlugar.model;

public class RelatorioQuantPlano {
    
    private String nomePlano = "";
    private String nomeUsuario = "";
    private int quantidade = 0;
    private double valorFinanceiro = 0d;    

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
    
}
