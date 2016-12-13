package com.busqueumlugar.model;


import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "cidades")
public class Cidades implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
    private Integer id;	
    
    @Column(name = "nome")
    private String nome;    
    
    @Column(name = "uf")
    private String uf;    
    
    @Column(name = "cep2")
    private String cep2;    
        
    @Column(name = "estado_cod")
    private int estadoCod;
        
    @Column(name = "cep")
    private String cep;
    
    @Transient
    private int quantidade = 0; // campo usado para relatorios
    
    @Transient
    private double valorTotal = 0d; // campo usado para relatorios
    
    @Transient
    private double valorMedio = 0d; // campo usado para relatorios


    public Cidades() {
    }

    public Cidades(Integer id) {
        this.id = id;
    }

    public Cidades(Integer id, String nome, String uf, String cep2, int estadoCod, String cep) {
        this.id = id;
        this.nome = nome;
        this.uf = uf;
        this.cep2 = cep2;
        this.estadoCod = estadoCod;
        this.cep = cep;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getCep2() {
        return cep2;
    }

    public void setCep2(String cep2) {
        this.cep2 = cep2;
    }

    public int getEstadoCod() {
        return estadoCod;
    }

    public void setEstadoCod(int estadoCod) {
        this.estadoCod = estadoCod;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
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
        if (!(object instanceof Cidades)) {
            return false;
        }
        Cidades other = (Cidades) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.app.localidade.Cidades[ id=" + id + " ]";
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
