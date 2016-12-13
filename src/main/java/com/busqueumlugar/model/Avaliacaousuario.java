package com.busqueumlugar.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name = "avaliacaousuario")
public class Avaliacaousuario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)    
    @Column(name = "id")
    private Long id;    
        
    @Column(name = "idUsuarioCliente")
    private long idUsuarioCliente;    
            
    @Column(name = "idUsuario")
    private long idUsuario;    
        
    @Column(name = "dataAvaliacao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataAvaliacao;   
    
    @Column(name = "statusAvaliacao")
    private String statusAvaliacao;

    public Avaliacaousuario() {
    }

    public Avaliacaousuario(Long id) {
        this.id = id;
    }

    public Avaliacaousuario(Long id, long idUsuarioCliente, long idUsuario, Date dataAvaliacao, String statusAvaliacao) {
        this.id = id;
        this.idUsuarioCliente = idUsuarioCliente;
        this.idUsuario = idUsuario;
        this.dataAvaliacao = dataAvaliacao;
        this.statusAvaliacao = statusAvaliacao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getIdUsuarioCliente() {
        return idUsuarioCliente;
    }

    public void setIdUsuarioCliente(long idUsuarioCliente) {
        this.idUsuarioCliente = idUsuarioCliente;
    }

    public long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Date getDataAvaliacao() {
        return dataAvaliacao;
    }

    public void setDataAvaliacao(Date dataAvaliacao) {
        this.dataAvaliacao = dataAvaliacao;
    }

    public String getStatusAvaliacao() {
        return statusAvaliacao;
    }

    public void setStatusAvaliacao(String statusAvaliacao) {
        this.statusAvaliacao = statusAvaliacao;
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
        if (!(object instanceof Avaliacaousuario)) {
            return false;
        }
        Avaliacaousuario other = (Avaliacaousuario) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.app.usuario.Avaliacaousuario[ id=" + id + " ]";
    }
    
}
