package com.busqueumlugar.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name = "estados")
public class Estados implements Serializable  {	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
 	@Column(name = "id")
    private Integer id;    

    @Column(name = "nome")
    private String nome;    

    @Column(name = "uf")
    private String uf;    
    
    @Column(name = "ibge")
    private int ibge;
    
    @Transient
    private int quantidade = 0; // campo usado para relatorios
    
    @Transient
    private double valorTotal = 0d; // campo usado para relatorios
    
    @Transient
    private double valorMedio = 0d; // campo usado para relatorios

    public Estados() {
    }

    public Estados(Integer id) {
        this.id = id;
    }

    public Estados(Integer id, String nome, String uf, int ibge) {
        this.id = id;
        this.nome = nome;
        this.uf = uf;
        this.ibge = ibge;
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

    public int getIbge() {
        return ibge;
    }

    public void setIbge(int ibge) {
        this.ibge = ibge;
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
        if (!(object instanceof Estados)) {
            return false;
        }
        Estados other = (Estados) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.app.estado.Estados[ id=" + id + " ]";
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
