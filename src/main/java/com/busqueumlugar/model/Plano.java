/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.busqueumlugar.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "plano")
public class Plano implements Serializable {
    
	private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    
    @Column(name = "id")
    private Long id;    
    
    @Column(name = "nome")
    private String nome;
    
    @Column(name = "quantFotos")
    private Integer quantFotos;
    
    @Column(name = "quantImoveisIndicacao")
    private Integer quantImoveisIndicacao;
    
    @Column(name = "quantEmailsPorImovel")
    private Integer quantEmailsPorImovel;
    
    @Column(name = "duracaoPlano")
    private Integer duracaoPlano;
    
    @Column(name = "habilitaCompartilhamento")
    private String habilitaCompartilhamento; // indica se o usuario pode ou nao enviar solicitacao de compartilhamento
    
    @Column(name = "quantEnvioSolCompart")
    private Integer quantEnvioSolCompart; // indica o limite maximo de solicitacoes de compartilhamento que um usuario pode enviar
    
    @Column(name = "quantCadastroImoveis")
    private Integer quantCadastroImoveis; 
    
    @Column(name = "quantFotosImovel")
    private Integer quantFotosImovel;  
    
    @Column(name = "habilitaEnvioSolParceria")
    private String habilitaEnvioSolParceria;
    
    @Column(name = "habilitaEnvioMensagens")
    private String habilitaEnvioMensagens;  
    
    @Column(name = "valorPlano")
    private double valorPlano;  
    
    @Transient
    private String servicosAssociados = "";
    
    public Plano() {
    }

    public Plano(Long id) {
        this.id = id;
    }

    public Plano(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getQuantFotos() {
        return quantFotos;
    }

    public void setQuantFotos(Integer quantFotos) {
        this.quantFotos = quantFotos;
    }

    public Integer getQuantImoveisIndicacao() {
        return quantImoveisIndicacao;
    }

    public void setQuantImoveisIndicacao(Integer quantImoveisIndicacao) {
        this.quantImoveisIndicacao = quantImoveisIndicacao;
    }

    public Integer getQuantEmailsPorImovel() {
        return quantEmailsPorImovel;
    }

    public void setQuantEmailsPorImovel(Integer quantEmailsPorImovel) {
        this.quantEmailsPorImovel = quantEmailsPorImovel;
    }

    public Integer getDuracaoPlano() {
        return duracaoPlano;
    }

    public void setDuracaoPlano(Integer duracaoPlano) {
        this.duracaoPlano = duracaoPlano;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Plano)) {
            return false;
        }
        Plano other = (Plano) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.app.plano.Plano[ id=" + id + " ]";
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
     * @return the servicosAssociados
     */
    public String getServicosAssociados() {
        return servicosAssociados;
    }

    /**
     * @param servicosAssociados the servicosAssociados to set
     */
    public void setServicosAssociados(String servicosAssociados) {
        this.servicosAssociados = servicosAssociados;
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
