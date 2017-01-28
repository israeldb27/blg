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
@Table(name = "bairros")
public class Bairros implements Serializable {

	private static final long serialVersionUID = -8146697212198211682L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;	
     
    @Column(name = "uf")
    private String uf;    
    
    @Column(name = "cidade")
    private String cidade;    
    
    @Column(name = "nome")
    private String nome;
    
    @Transient
    private String nomeCidade;
    
    @Transient
    private int quantidade = 0; // campo usado para relatorios
    
    @Transient
    private double valorTotal = 0d; // campo usado para relatorios
    
    @Transient
    private double valorMedio = 0d; // campo usado para relatorios
    
    public Bairros() {
    }

    public Bairros(Integer id) {
        this.id = id;
    }

    public Bairros(Integer id, String uf, String cidade, String nome) {
        this.id = id;
        this.uf = uf;
        this.cidade = cidade;
        this.nome = nome;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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
        if (!(object instanceof Bairros)) {
            return false;
        }
        Bairros other = (Bairros) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.app.localidade.Bairros[ id=" + id + " ]";
    }

    /**
     * @return the nomeCidade
     */
    public String getNomeCidade() {
        return nomeCidade;
    }

    /**
     * @param nomeCidade the nomeCidade to set
     */
    public void setNomeCidade(String nomeCidade) {
        this.nomeCidade = nomeCidade;
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
     * @return the valorTotal
     */
    public double getValorTotal() {
        return valorTotal;
    }

    /**
     * @param valorTotal the valorTotal to set
     */
    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    /**
     * @return the valorMedio
     */
    public double getValorMedio() {
        return valorMedio;
    }

    /**
     * @param valorMedio the valorMedio to set
     */
    public void setValorMedio(double valorMedio) {
        this.valorMedio = valorMedio;
    }
    
}
