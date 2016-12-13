package com.busqueumlugar.form;


public class PlanoForm {
    
    private Long id;
    private String nome = "";
    private Integer quantFotos;
    private Integer quantImoveisIndicacao;    
    private Integer quantEmailsPorImovel;    
    private Integer duracaoPlano;          
    private String habilitaCompartilhamento = "";
    private Integer quantEnvioSolCompart;    
    private Integer quantCadastroImoveis;         
    private Integer quantFotosImovel; 
    private String habilitaEnvioSolParceria;
    private String habilitaEnvioMensagens;
    private double valorPlano;


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
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the quantFotos
     */
    public Integer getQuantFotos() {
        return quantFotos;
    }

    /**
     * @param quantFotos the quantFotos to set
     */
    public void setQuantFotos(Integer quantFotos) {
        this.quantFotos = quantFotos;
    }

    /**
     * @return the quantImoveisIndicacao
     */
    public Integer getQuantImoveisIndicacao() {
        return quantImoveisIndicacao;
    }

    /**
     * @param quantImoveisIndicacao the quantImoveisIndicacao to set
     */
    public void setQuantImoveisIndicacao(Integer quantImoveisIndicacao) {
        this.quantImoveisIndicacao = quantImoveisIndicacao;
    }

    /**
     * @return the quantEmailsPorImovel
     */
    public Integer getQuantEmailsPorImovel() {
        return quantEmailsPorImovel;
    }

    /**
     * @param quantEmailsPorImovel the quantEmailsPorImovel to set
     */
    public void setQuantEmailsPorImovel(Integer quantEmailsPorImovel) {
        this.quantEmailsPorImovel = quantEmailsPorImovel;
    }

    /**
     * @return the duracaoPlano
     */
    public Integer getDuracaoPlano() {
        return duracaoPlano;
    }

    /**
     * @param duracaoPlano the duracaoPlano to set
     */
    public void setDuracaoPlano(Integer duracaoPlano) {
        this.duracaoPlano = duracaoPlano;
    }

    /**
     * @return the habilitaCompartilhamento
     */
    public String getHabilitaCompartilhamento() {
        return habilitaCompartilhamento;
    }

    /**
     * @param habilitaCompartilhamento the habilitaCompartilhamento to set
     */
    public void setHabilitaCompartilhamento(String habilitaCompartilhamento) {
        this.habilitaCompartilhamento = habilitaCompartilhamento;
    }

    /**
     * @return the quantEnvioSolCompart
     */
    public Integer getQuantEnvioSolCompart() {
        return quantEnvioSolCompart;
    }

    /**
     * @param quantEnvioSolCompart the quantEnvioSolCompart to set
     */
    public void setQuantEnvioSolCompart(Integer quantEnvioSolCompart) {
        this.quantEnvioSolCompart = quantEnvioSolCompart;
    }

    /**
     * @return the quantCadastroImoveis
     */
    public Integer getQuantCadastroImoveis() {
        return quantCadastroImoveis;
    }

    /**
     * @param quantCadastroImoveis the quantCadastroImoveis to set
     */
    public void setQuantCadastroImoveis(Integer quantCadastroImoveis) {
        this.quantCadastroImoveis = quantCadastroImoveis;
    }

    /**
     * @return the quantFotosImovel
     */
    public Integer getQuantFotosImovel() {
        return quantFotosImovel;
    }

    /**
     * @param quantFotosImovel the quantFotosImovel to set
     */
    public void setQuantFotosImovel(Integer quantFotosImovel) {
        this.quantFotosImovel = quantFotosImovel;
    }

    /**
     * @return the habilitaEnvioSolParceria
     */
    public String getHabilitaEnvioSolParceria() {
        return habilitaEnvioSolParceria;
    }

    /**
     * @param habilitaEnvioSolParceria the habilitaEnvioSolParceria to set
     */
    public void setHabilitaEnvioSolParceria(String habilitaEnvioSolParceria) {
        this.habilitaEnvioSolParceria = habilitaEnvioSolParceria;
    }

    /**
     * @return the habilitaEnvioMensagens
     */
    public String getHabilitaEnvioMensagens() {
        return habilitaEnvioMensagens;
    }

    /**
     * @param habilitaEnvioMensagens the habilitaEnvioMensagens to set
     */
    public void setHabilitaEnvioMensagens(String habilitaEnvioMensagens) {
        this.habilitaEnvioMensagens = habilitaEnvioMensagens;
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
    
}
